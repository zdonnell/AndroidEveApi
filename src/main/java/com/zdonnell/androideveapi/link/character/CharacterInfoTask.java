package com.zdonnell.androideveapi.link.character;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.character.CharacterInfoParser;
import com.zdonnell.androideveapi.eve.character.CharacterInfoResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.CacheDatabase;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.CharacterInfoData;

/**
 * AsyncTask to retrieve character sheet information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class CharacterInfoTask extends AsyncTask<Void, Void, CharacterInfoResponse> implements IApiTask<CharacterInfoResponse>
{	
	private CacheDatabase cacheDatabase;
	
	private APIExceptionCallback<CharacterInfoResponse> callback;
	private ApiAuth<?> apiAuth;
	private Context context;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	
	private boolean cacheExists = false, cacheValid = false;
	
	private CharacterInfoResponse cachedData;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public CharacterInfoTask(APIExceptionCallback<CharacterInfoResponse> callback, ApiAuth<?> apiAuth, Context context)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		this.context = context;
		
		cacheDatabase = new CacheDatabase(context);
	}
	
	@Override
	protected CharacterInfoResponse doInBackground(Void... params)
	{
		int requestHash = apiAuth.hashCode() + requestTypeHash();
		
		cacheValid = cacheDatabase.cacheValid(requestHash);
		cacheExists = cacheDatabase.cacheExists(requestHash);
				
		if (cacheValid)
		{
			return buildResponseFromDatabase();
		}
		else
		{
			Log.d("TEST", "LOADING CHARACTER INFO FROM SERVER");
			
			// The cache is out of date (invalid) but load it anyway while we contact the API server
			if (cacheExists) 
			{
				cachedData = buildResponseFromDatabase();
				publishProgress();
			}
			else callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_NOT_FOUND);
 	
			CharacterInfoParser parser = CharacterInfoParser.getInstance();		
			CharacterInfoResponse response = null;
						
	        try 
	        { 
	        	response = parser.getResponse(apiAuth);	        	
	        	cacheDatabase.updateCache(requestHash, response.getCachedUntil());
	        	
	        	new CharacterInfoData(context).setCharacterInfo(response);
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
	protected void onPostExecute(CharacterInfoResponse response) 
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

	@Override
	protected void onProgressUpdate(Void... progress)
	{		
		callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
		callback.onUpdate(cachedData);
	}
	
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.CHARACTER_INFO.getPage()).hashCode();
	}

	public CharacterInfoResponse buildResponseFromDatabase() 
	{	
		return new CharacterInfoData(context).getCharacterInfo(apiAuth.getCharacterID().intValue());
	}
}

