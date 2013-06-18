package com.zdonnell.androideveapi.map.kills;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class KillsParser extends AbstractListParser<KillsHandler, KillsResponse, ApiSystemKills> {
	public KillsParser() {
		super(KillsResponse.class, 2, ApiPath.MAP, ApiPage.KILLS, KillsHandler.class);
	}

	public static KillsParser getInstance() {
		return new KillsParser();
	}

	@Override
	public KillsResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}