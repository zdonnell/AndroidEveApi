package com.zdonnell.androideveapi.link.character;

import java.util.Collection;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.character.assetlist.AssetListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.CacheDatabase;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.AssetsData;
import com.zdonnell.androideveapi.shared.assetlist.AssetListResponse;
import com.zdonnell.androideveapi.shared.assetlist.EveAsset;

/**
 * AsyncTask to retrieve character asset information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class AssetsTask extends AsyncTask<Void, Void, AssetListResponse> implements IApiTask<AssetListResponse>
{		
	private CacheDatabase cacheDatabase;
	
	private APIExceptionCallback<AssetListResponse> callback;
	private ApiAuth<?> apiAuth;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
			
	private AssetListResponse cachedData;
	private AssetsData assetsDatabase;
	
	private boolean cacheExists = false, cacheValid = false;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public AssetsTask(APIExceptionCallback<AssetListResponse> callback, ApiAuth<?> apiAuth, Context context)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		
		cacheDatabase = new CacheDatabase(context);
		assetsDatabase = new AssetsData(context);
	}
	
	@Override
	protected AssetListResponse doInBackground(Void... params)
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
 	
			AssetListParser parser = AssetListParser.getInstance();		
			AssetListResponse response = null;
						
	        try 
	        { 
	        	response = parser.getResponse(apiAuth);
	        		        	
	        	cacheDatabase.updateCache(requestHash, response.getCachedUntil());
	        	assetsDatabase.setAssets(apiAuth.getCharacterID().intValue(), response.getAll());
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
	protected void onPostExecute(AssetListResponse response) 
	{	
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
		callback.onUpdate(cachedData);
	}
	
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.ASSET_LIST.getPage()).hashCode();
	}

	public AssetListResponse buildResponseFromDatabase() 
	{		
		AssetListResponse response = new AssetListResponse();
		
		Collection<EveAsset<EveAsset<?>>> assets = assetsDatabase.getAssets(apiAuth.getCharacterID().intValue());
		for (EveAsset<EveAsset<?>> asset : assets) response.add(asset);
				
		return response;
	}
}

