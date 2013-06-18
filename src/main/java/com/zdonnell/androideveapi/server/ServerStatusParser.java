package com.zdonnell.androideveapi.server;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class ServerStatusParser extends AbstractApiParser<ServerStatusResponse> {
	public ServerStatusParser() {
		super(ServerStatusResponse.class, 2, ApiPath.SERVER, ApiPage.SERVER_STATUS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new ServerStatusHandler();
	}

	public static ServerStatusParser getInstance() {
		return new ServerStatusParser();
	}

	public ServerStatusResponse getServerStatus() throws ApiException {
		return getResponse();
	}
}