package com.zdonnell.androideveapi.link.character;

import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.character.skill.queue.ApiSkillQueueItem;
import com.zdonnell.androideveapi.character.skill.queue.SkillQueueParser;
import com.zdonnell.androideveapi.character.skill.queue.SkillQueueResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.CacheDatabase;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.SkillQueueData;

/**
 * AsyncTask to retrieve character sheet information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class SkillQueueTask extends AsyncTask<Void, Void, SkillQueueResponse> implements IApiTask<SkillQueueResponse>
{	
	private CacheDatabase cacheDatabase;
	
	private APIExceptionCallback<SkillQueueResponse> callback;
	private ApiAuth<?> apiAuth;
	private Context context;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	
	private boolean cacheExists = false, cacheValid = false;
	
	private SkillQueueResponse cachedData;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public SkillQueueTask(APIExceptionCallback<SkillQueueResponse> callback, ApiAuth<?> apiAuth, Context context)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		this.context = context;
		
		cacheDatabase = new CacheDatabase(context);
	}
	
	@Override
	protected SkillQueueResponse doInBackground(Void... params)
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
			// The cache is out of date (invalid) but load it anyway while we contact the API server
			if (cacheExists) 
			{
				cachedData = buildResponseFromDatabase();
				publishProgress();
			}
			else callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_NOT_FOUND);
 	
			SkillQueueParser parser = SkillQueueParser.getInstance();		
			SkillQueueResponse response = null;
						
	        try 
	        { 
	        	response = parser.getResponse(apiAuth);
	        	
	        	cacheDatabase.updateCache(requestHash, response.getCachedUntil());
	        	new SkillQueueData(context).setQueueSkills(apiAuth.getCharacterID().intValue(), response.getAll());
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
	protected void onPostExecute(SkillQueueResponse response) 
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
		return ApiPath.CHARACTER.getPath().concat(ApiPage.SKILL_QUEUE.getPage()).hashCode();
	}

	public SkillQueueResponse buildResponseFromDatabase() 
	{
		SkillQueueResponse response = new SkillQueueResponse();
		
		SkillQueueData skillQueueData = new SkillQueueData(context);
		Set<ApiSkillQueueItem> queue = skillQueueData.getQueue(apiAuth.getCharacterID().intValue());
		for (ApiSkillQueueItem queuedSkill : queue) response.add(queuedSkill);
		
		return response;
	}
}

