package com.zdonnell.androideveapi.shared.standings;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractStandingsParser extends AbstractApiParser<StandingsResponse> {

	protected AbstractStandingsParser(ApiPath path) {
		super(StandingsResponse.class, 2, path, ApiPage.STANDINGS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new StandingsHandler();
	}
	
	@Override
	public StandingsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}