package com.zdonnell.androideveapi.link.server;

import android.content.Context;

import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;
import com.zdonnell.androideveapi.server.ServerStatusParser;
import com.zdonnell.androideveapi.server.ServerStatusResponse;

public class ServerStatusTask extends APITask<Void, Void, ServerStatusResponse>
{
	public ServerStatusTask(APIExceptionCallback<ServerStatusResponse> callback, Context context)
	{
		super(callback, context, false, new EveApiInteraction<ServerStatusResponse>()
		{
			@Override
			public ServerStatusResponse perform() throws ApiException 
			{
				ServerStatusParser parser = ServerStatusParser.getInstance();		
				return parser.getServerStatus();
			}
		});
	}

	public int requestTypeHash() { return 0; /* cache not used */ }

	public ServerStatusResponse buildResponseFromDatabase() { return null; /* cache not used */ }
}
