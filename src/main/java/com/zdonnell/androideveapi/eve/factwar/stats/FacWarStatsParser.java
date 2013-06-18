package com.zdonnell.androideveapi.eve.factwar.stats;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class FacWarStatsParser extends AbstractApiParser<FacWarStatsResponse> {
	private FacWarStatsParser() {
		super(FacWarStatsResponse.class, 2, ApiPath.EVE, ApiPage.FACT_WAR_STATS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new FacWarStatsHandler();
	}

	public static FacWarStatsParser getInstance() {
		return new FacWarStatsParser();
	}

	@Override
	public FacWarStatsResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}