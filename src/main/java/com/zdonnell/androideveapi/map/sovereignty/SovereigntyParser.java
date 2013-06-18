package com.zdonnell.androideveapi.map.sovereignty;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class SovereigntyParser extends AbstractListParser<SovereigntyHandler, SovereigntyResponse, ApiSystemSovereignty> {
	public SovereigntyParser() {
		super(SovereigntyResponse.class, 1, ApiPath.MAP, ApiPage.SOVEREIGNTY, SovereigntyHandler.class);
	}

	public static SovereigntyParser getInstance() {
		return new SovereigntyParser();
	}

	@Override
	public SovereigntyResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}