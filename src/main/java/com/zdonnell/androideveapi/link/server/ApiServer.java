package com.zdonnell.androideveapi.link.server;

import android.content.Context;

import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.server.ServerStatusResponse;

public class ApiServer 
{	
	private final Context context;
	
	ApiServer(Context context)
	{
		this.context = context;
	}
	
	public void status(ApiExceptionCallback<ServerStatusResponse> callback)
	{
		new ServerStatusTask(callback, context).execute(); 
	}
}
