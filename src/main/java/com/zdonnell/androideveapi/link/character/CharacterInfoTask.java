package com.zdonnell.androideveapi.link.character;

import android.content.Context;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.character.CharacterInfoParser;
import com.zdonnell.androideveapi.eve.character.CharacterInfoResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;
import com.zdonnell.androideveapi.link.database.CharacterInfoData;

/**
 * AsyncTask to retrieve character information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class CharacterInfoTask extends APITask<Void, Void, CharacterInfoResponse>
{	
	final private ApiAuth<?> apiAuth;
	
	public CharacterInfoTask(APIExceptionCallback<CharacterInfoResponse> callback, final ApiAuth<?> apiAuth, final Context context)
	{
		super(callback, context, true, new EveApiInteraction<CharacterInfoResponse>()
		{
			@Override
			public CharacterInfoResponse perform() throws ApiException 
			{
				CharacterInfoParser parser = CharacterInfoParser.getInstance();		
				CharacterInfoResponse response = parser.getResponse(apiAuth);

		        new CharacterInfoData(context).setCharacterInfo(response);;
		        
		        return response;
			}
		});
		
		this.apiAuth = apiAuth;
	}
	
	@Override
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.CHARACTER_INFO.getPage()).hashCode();
	}

	@Override
	public CharacterInfoResponse buildResponseFromDatabase() 
	{	
		return new CharacterInfoData(context).getCharacterInfo(apiAuth.getCharacterID().intValue());
	}
}