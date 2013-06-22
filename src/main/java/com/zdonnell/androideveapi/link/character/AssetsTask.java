package com.zdonnell.androideveapi.link.character;

import java.util.Collection;

import android.content.Context;

import com.zdonnell.androideveapi.character.assetlist.AssetListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;
import com.zdonnell.androideveapi.link.database.AssetsData;
import com.zdonnell.androideveapi.shared.assetlist.AssetListResponse;
import com.zdonnell.androideveapi.shared.assetlist.EveAsset;

/**
 * AsyncTask to retrieve character asset information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class AssetsTask extends APITask<Void, Void, AssetListResponse>
{			
	public AssetsTask(APIExceptionCallback<AssetListResponse> callback, final ApiAuth<?> apiAuth, final Context context)
	{
		super(callback, context, true, apiAuth, new EveApiInteraction<AssetListResponse>()
		{
			@Override
			public AssetListResponse perform() throws ApiException 
			{
				AssetListParser parser = AssetListParser.getInstance();		
				AssetListResponse response = parser.getResponse(apiAuth);
 		        	
		        new AssetsData(context).setAssets(apiAuth.getCharacterID().intValue(), response.getAll());
		        
		        return response;
			}
		});
	}
	
	@Override
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.ASSET_LIST.getPage()).hashCode();
	}

	@Override
	public AssetListResponse buildResponseFromDatabase() 
	{		
		AssetListResponse response = new AssetListResponse();
		
		Collection<EveAsset<EveAsset<?>>> assets = new AssetsData(context).getAssets(apiAuth.getCharacterID().intValue());
		for (EveAsset<EveAsset<?>> asset : assets) response.add(asset);
				
		return response;
	}
}

