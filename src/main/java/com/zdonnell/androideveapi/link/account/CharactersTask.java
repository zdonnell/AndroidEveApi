package com.zdonnell.androideveapi.link.account;

import android.os.AsyncTask;

import com.zdonnell.androideveapi.account.characters.CharactersParser;
import com.zdonnell.androideveapi.account.characters.CharactersResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;

public class CharactersTask extends AsyncTask<Void, Void, CharactersResponse>
{
	private APIExceptionCallback<CharactersResponse> callback;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	private ApiAuth<?> apiAuth;
	
	public CharactersTask(APIExceptionCallback<CharactersResponse> callback, ApiAuth<?> apiAuth)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		
		// We don't cache characters resposnes, so just notify the callback that we didn't find any
		callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_NOT_FOUND);
	}
	
	@Override
	protected CharactersResponse doInBackground(Void... params)
	{
		CharactersParser parser = CharactersParser.getInstance();		
		CharactersResponse response = null;
		
        try { response = parser.getResponse(apiAuth); }
		catch (ApiException e) 
		{
			apiExceptionOccured = true;
			exception = e;
		}
        
        return response;
	}
	
	@Override
	protected void onPostExecute(CharactersResponse response) 
	{
		if (apiExceptionOccured) 
		{
			callback.onError(response, exception);
			callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
		}
		else 
		{
			callback.onUpdate(response);
			callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
		}
    }
}
