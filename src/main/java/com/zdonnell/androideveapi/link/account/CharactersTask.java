package com.zdonnell.androideveapi.link.account;

import android.content.Context;

import com.zdonnell.androideveapi.account.characters.CharactersParser;
import com.zdonnell.androideveapi.account.characters.CharactersResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.ApiTask;

public class CharactersTask extends ApiTask<Void, Void, CharactersResponse>
{
	public CharactersTask(ApiExceptionCallback<CharactersResponse> callback, final Context context, final ApiAuth<?> apiAuth)
	{
		super(callback, context, false, apiAuth, new EveApiInteraction<CharactersResponse>()
		{
			@Override
			public CharactersResponse perform() throws ApiException 
			{
				CharactersParser parser = CharactersParser.getInstance();		
				return parser.getResponse(apiAuth);
			}
			
		});
	}
}
