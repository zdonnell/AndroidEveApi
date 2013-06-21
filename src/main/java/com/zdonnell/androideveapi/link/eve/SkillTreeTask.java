package com.zdonnell.androideveapi.link.eve;

import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkill;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkillGroup;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeParser;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.CacheDatabase;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.SkillTreeData;

public class SkillTreeTask extends AsyncTask<Void, Void, SkillTreeResponse> implements IApiTask<SkillTreeResponse>
{
	private APIExceptionCallback<SkillTreeResponse> callback;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	
	private CacheDatabase cacheDatabase;
	
	private Context context;
	
	private boolean cacheExists = false, cacheValid = false;
	
	private SkillTreeResponse cachedData;
	
	public SkillTreeTask(APIExceptionCallback<SkillTreeResponse> callback, Context context)
	{
		this.callback = callback;
		this.context = context;
		
		cacheDatabase = new CacheDatabase(context);
	}
	
	@Override
	protected SkillTreeResponse doInBackground(Void... params)
	{		
		int requestHash = requestTypeHash();
		
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
			
			SkillTreeParser parser = SkillTreeParser.getInstance();		
			SkillTreeResponse response = null;
			
	        try 
	        { 
	        	response = parser.getResponse();
	        	fixSkillGroups(response);
	        	
	        	cacheDatabase.updateCache(requestHash, response.getCachedUntil());
	        	new SkillTreeData(context).setSkillTree(response.getAll());
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
	protected void onProgressUpdate(Void... progress)
	{		
		callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
		callback.onUpdate(cachedData);
	}
	
	@Override
	protected void onPostExecute(SkillTreeResponse response) 
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
	 * Removes duplicate groups from a {@link SkillTreeResponse}
	 * 
	 * @param response
	 */
	private void fixSkillGroups(SkillTreeResponse response)
	{
		SparseArray<ApiSkillGroup> correctedSkillGroups = new SparseArray<ApiSkillGroup>();
		
		for (ApiSkillGroup group : response.getAll())
		{
			// This is the first time we have seen a group of this ID, add it to the corrected list
			if (correctedSkillGroups.get(group.getGroupID()) == null)
			{
				correctedSkillGroups.put(group.getGroupID(), group);
			}
			// The group exists in the corrected list already, add all it's skills to the existing group in the corrected list
			else
			{
				for (ApiSkill skill : group.getSkills()) correctedSkillGroups.get(group.getGroupID()).add(skill);
			}
		}
		
		// Clear the "unfixed" groups, and add back the fixed ones
		response.getAll().clear();
		for (int i = 0; i < correctedSkillGroups.size(); i++) response.add(correctedSkillGroups.valueAt(i));
	}

	public int requestTypeHash() 
	{
		return ApiPath.EVE.getPath().concat(ApiPage.SKILL_TREE.getPage()).hashCode();
	}

	public SkillTreeResponse buildResponseFromDatabase() 
	{
		SkillTreeResponse response = new SkillTreeResponse();
		
		SkillTreeData skillTree = new SkillTreeData(context);
		for (ApiSkillGroup group : skillTree.getSkillTree()) response.add(group);
		
		return response;
	}
}
