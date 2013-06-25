package com.zdonnell.androideveapi.link.server;

import android.content.Context;

import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.ApiTask;
import com.zdonnell.androideveapi.server.ServerStatusParser;
import com.zdonnell.androideveapi.server.ServerStatusResponse;

public class ServerStatusTask extends ApiTask<Void, Void, ServerStatusResponse> {
	public ServerStatusTask(ApiExceptionCallback<ServerStatusResponse> callback, Context context) {
		super(callback, context, false, null, new EveApiInteraction<ServerStatusResponse>() {
			public ServerStatusResponse perform() throws ApiException {
				ServerStatusParser parser = ServerStatusParser.getInstance();		
				return parser.getServerStatus();
			}
		});
	}
}
