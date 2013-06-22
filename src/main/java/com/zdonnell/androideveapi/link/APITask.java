package com.zdonnell.androideveapi.link;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiResponse;
import com.zdonnell.androideveapi.exception.ApiException;

/**
 * Base AsyncTask for acquiring API Data
 * 
 * @author Zach
 *
 * @param <ExecuteParameter>
 * @param <ProgressParameter>
 * @param <Response>
 */
public abstract class APITask<ExecuteParameter, ProgressParameter, Response extends ApiResponse> extends AsyncTask<ExecuteParameter, ProgressParameter, Response>
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
	 * Callback to provide the the acquired response to.
	 */
	final private APIExceptionCallback<Response> callback;
	
	/**
	 * Code to run to generate the response, provided by subclass
	 */
	final private EveApiInteraction<Response> apiInteraction;
	
	/**
	 * Stored response built from cached data, if available
	 */
	protected Response cachedData;
	
	/**
	 * Database storing hash codes and the time their cache data expires
	 */
	private CacheDatabase cacheDatabase;
	
	/**
	 * Flags whether an exception occurred when the {@link #apiInteraction} was run
	 */
	private boolean apiExceptionOccured = false;
	
	/**
	 * Stores the ApiException if one occurs in {@link #apiInteraction}
	 */
	private ApiException exception;
	
	/**
	 * Application context
	 */
	final protected Context context;
	
	/**
	 * Whether the task should check the cache database. This is not needed for some API Requests.
	 */
	final private boolean useCache;
	
	/**
	 * Reference to the apiAuthorization used
	 */
	protected final ApiAuth<?> apiAuth;
	
	public APITask(APIExceptionCallback<Response> callback, Context context, boolean useCache, ApiAuth<?> apiAuth, EveApiInteraction<Response> apiInteraction)
	{
		this.callback = callback;
		this.apiInteraction = apiInteraction;
		this.useCache = useCache;
		this.context = context;
		this.apiAuth = apiAuth;
		
		this.cacheDatabase = new CacheDatabase(context);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Response doInBackground(ExecuteParameter... params) 
	{
		int requestHash = requestTypeHash();
		if (apiAuth != null) requestHash += apiAuth.hashCode();
		
		// cacheExists and cacheValid are only true if we actually want to use cache
		cacheValid = useCache && cacheDatabase.cacheValid(requestHash);
		cacheExists = useCache && cacheDatabase.cacheExists(requestHash);
		
		if (cacheValid)
		{
			return buildResponseFromDatabase();
		}
		else
		{
			// The cache is out of date (invalid) but load it anyway while we contact the API server
			if (cacheExists) 
			{
				cachedData = buildResponseFromDatabase();
				publishProgress();
			}
			else callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_NOT_FOUND);
			
			Response response = null;
			
	        try 
	        { 
	        	response = apiInteraction.perform();	        	
	        	cacheDatabase.updateCache(requestHash, response.getCachedUntil());
	        }
			catch (ApiException e) 
			{
				apiExceptionOccured = true;
				exception = e;
			}
	        
	        return response;
		}
	}
	
	@Override
	protected void onProgressUpdate(ProgressParameter... progress)
	{		
		callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
		callback.onUpdate(cachedData);
	}
	
	@Override
	protected void onPostExecute(Response response) 
	{
		// We can arrive here one of two ways, if the cache was still valid, or if it was invalid
		// and a server response was acquired, check which it is.
		if (cacheValid)
		{
			callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_VALID);
			callback.onUpdate(response);
		}
		else
		{
			if (apiExceptionOccured) 
			{
				callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
				callback.onError(response, exception);
			}
			else 
			{
				callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
				callback.onUpdate(response);
			}
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
	
	/**
	 * Interface to be implemented for interaction with the eveapi data specific
	 * to the current request.
	 * 
	 * @author Zach
	 *
	 * @param <Response>
	 * @see {@link APITask#apiInteraction}
	 */
	protected interface EveApiInteraction<Response> 
	{
		Response perform() throws ApiException;
	}

}
