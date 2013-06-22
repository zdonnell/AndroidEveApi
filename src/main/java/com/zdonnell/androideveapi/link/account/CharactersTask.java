package com.zdonnell.androideveapi.link.account;

import android.content.Context;

import com.zdonnell.androideveapi.account.characters.CharactersParser;
import com.zdonnell.androideveapi.account.characters.CharactersResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;

public class CharactersTask extends APITask<Void, Void, CharactersResponse>
{
	public CharactersTask(APIExceptionCallback<CharactersResponse> callback, final Context context, final ApiAuth<?> apiAuth)
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

	@Override
	protected int requestTypeHash() { return 0; /* no hash needed, not using cache database */ }

	@Override
	protected CharactersResponse buildResponseFromDatabase() { return null; /* not using cache database */ }
}
