package com.zdonnell.androideveapi.link;

import android.content.Context;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiResponse;

/**
 * Base class for Tasks that need to maintain a cache of the ApiResponse.
 * 
 * @author Zach
 *
 * @param <ExecuteParameter>
 * @param <ProgressParameter>
 * @param <Response>
 */
public abstract class ApiCachingTask<ExecuteParameter, ProgressParameter, Response extends ApiResponse> extends ApiTask<ExecuteParameter, ProgressParameter, Response>
{
	/**
	 * True if the {@link CacheDatabase} has an entry for the given Response hash and {@link #useCache} is true.
	 */
	private boolean cacheExists = false;
	
	/**
	 * True if the {@link CacheDatabase} has an entry for the given Response hash that
	 * has not yet expired and {@link #useCache} is true.
	 */
	private boolean cacheValid = false;
	
	/**
	 * Database storing hash codes and the time their cache data expires
	 */
	private CacheDatabase cacheDatabase;
	
	/**
	 * Stored response built from cached data, if available
	 */
	protected Response cachedData;
	
	
	public ApiCachingTask(ApiExceptionCallback<Response> callback, Context context, boolean useCache, ApiAuth<?> apiAuth, EveApiInteraction<Response> apiInteraction) {
		super(callback, context, useCache, apiAuth, apiInteraction);
		this.cacheDatabase = new CacheDatabase(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response doInBackground(ExecuteParameter... params) 
	{
		int requestHash = requestTypeHash();
		if (apiAuth != null) requestHash += apiAuth.hashCode();
		
		cacheValid = cacheDatabase.cacheValid(requestHash);
		cacheExists = cacheDatabase.cacheExists(requestHash);
		
		if (cacheValid) {
			return buildResponseFromDatabase();
		} else {	
			// The cache is out of date (invalid) but load it anyway while we contact the API server
			if (cacheExists) {
				cachedData = buildResponseFromDatabase();
				publishProgress();
			} else callback.updateState(ApiExceptionCallback.STATE_CACHED_RESPONSE_NOT_FOUND);
			
			Response response = super.doInBackground(params);
			if (response != null) cacheDatabase.updateCache(requestHash, response.getCachedUntil());
			
	        return response;
		}
	}
	
	@Override
	protected void onProgressUpdate(ProgressParameter... progress)
	{		
		callback.updateState(ApiExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
		callback.onUpdate(cachedData);
	}
	
	@Override
	protected void onPostExecute(Response response) 
	{
		// We can arrive here one of two ways, if the cache was still valid, or if it was invalid
		// and a server response was acquired, check which it is.
		if (cacheValid) {
			callback.updateState(ApiExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_VALID);
			callback.onUpdate(response);
		} else {
			super.onPostExecute(response);
		}
    }
	
	/**
	 * Subclasses implement this to specify a unique hash for that request
	 * 
	 * @return
	 */
	protected abstract int requestTypeHash();
	
	/**
	 * Subclasses implement this to provide a Response built from the database
	 * 
	 * @return
	 */
	protected abstract Response buildResponseFromDatabase();
}
