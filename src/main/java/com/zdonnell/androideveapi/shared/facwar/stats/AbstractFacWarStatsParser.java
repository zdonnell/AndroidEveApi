package com.zdonnell.androideveapi.shared.facwar.stats;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class AbstractFacWarStatsParser extends AbstractApiParser<FacWarStatsResponse> {
	protected AbstractFacWarStatsParser(ApiPath path) {
		super(FacWarStatsResponse.class, 2, path, ApiPage.FACT_WAR_STATS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new FacWarStatsHandler();
	}

	@Override
	public FacWarStatsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}