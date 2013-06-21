package com.zdonnell.androideveapi.link.character;

import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.character.sheet.ApiAttributeEnhancer;
import com.zdonnell.androideveapi.character.sheet.ApiSkill;
import com.zdonnell.androideveapi.character.sheet.CharacterSheetParser;
import com.zdonnell.androideveapi.character.sheet.CharacterSheetResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.CacheDatabase;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.AttributesData;
import com.zdonnell.androideveapi.link.database.CharacterSheetData;
import com.zdonnell.androideveapi.link.database.SkillsData;

/**
 * AsyncTask to retrieve character sheet information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class CharacterSheetTask extends AsyncTask<Void, Void, CharacterSheetResponse> implements IApiTask<CharacterSheetResponse>
{	
	private CacheDatabase cacheDatabase;
	
	private APIExceptionCallback<CharacterSheetResponse> callback;
	private ApiAuth<?> apiAuth;
	private Context context;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	
	private boolean cacheExists = false, cacheValid = false;
	
	private CharacterSheetResponse cachedData;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public CharacterSheetTask(APIExceptionCallback<CharacterSheetResponse> callback, ApiAuth<?> apiAuth, Context context)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		this.context = context;
		
		cacheDatabase = new CacheDatabase(context);
	}
	
	@Override
	protected CharacterSheetResponse doInBackground(Void... params)
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
 	
			CharacterSheetParser parser = CharacterSheetParser.getInstance();		
			CharacterSheetResponse response = null;
						
	        try 
	        { 
	        	response = parser.getResponse(apiAuth);	        	
	        	cacheDatabase.updateCache(requestHash, response.getCachedUntil());
	        	
	        	new CharacterSheetData(context).setCharacterSheet(response);
	        	new SkillsData(context).storeSkills((int) response.getCharacterID(), response.getSkills());
	        	new AttributesData(context).setImplants((int) response.getCharacterID(), response.getAttributeEnhancers());
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
	protected void onPostExecute(CharacterSheetResponse response) 
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
		return ApiPath.CHARACTER.getPath().concat(ApiPage.CHARACTER_SHEET.getPage()).hashCode();
	}

	public CharacterSheetResponse buildResponseFromDatabase() 
	{
		CharacterSheetResponse response = new CharacterSheetData(context).getCharacterSheet(apiAuth.getCharacterID().intValue());
		
		// Get attributes
		Set<ApiAttributeEnhancer> implants = new AttributesData(context).getImplants(apiAuth.getCharacterID().intValue());
		for (ApiAttributeEnhancer enhancer : implants) response.addAttributeEnhancer(enhancer);
		
		// Get skills
		SkillsData skillsData = new SkillsData(context);
		Set<ApiSkill> skills = skillsData.getSkills(apiAuth.getCharacterID().intValue());
		for (ApiSkill s : skills) response.addSkill(s);
		
		return response;
	}
}

