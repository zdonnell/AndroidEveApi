package com.zdonnell.androideveapi.link.account;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.account.characters.CharactersResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;

public class ApiAccount 
{
	final private ApiAuth<?> apiAuth;
	final private Context context;
	
	public ApiAccount(ApiAuth<?> apiAuth, Context context)
	{
		this.apiAuth = apiAuth;
		this.context = context;
	}
	
	public void getCharacters(ApiExceptionCallback<CharactersResponse> callback)
	{
		new CharactersTask(callback, context, apiAuth).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
}
