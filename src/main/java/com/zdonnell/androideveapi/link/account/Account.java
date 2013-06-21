package com.zdonnell.androideveapi.link.account;

import android.os.AsyncTask;

import com.zdonnell.androideveapi.account.characters.CharactersResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.link.APIExceptionCallback;

public class Account 
{
	private ApiAuth<?> apiAuth;
	
	public Account(ApiAuth<?> apiAuth)
	{
		this.apiAuth = apiAuth;
	}
	
	public void getCharacters(APIExceptionCallback<CharactersResponse> callback)
	{
		new CharactersTask(callback, apiAuth).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
}
