package com.zdonnell.androideveapi.link.server;

import android.content.Context;

import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.server.ServerStatusResponse;

public class Server 
{	
	private final Context context;
	
	Server(Context context)
	{
		this.context = context;
	}
	
	public void status(APIExceptionCallback<ServerStatusResponse> callback)
	{
		new ServerStatusTask(callback, context).execute(); 
	}
}
