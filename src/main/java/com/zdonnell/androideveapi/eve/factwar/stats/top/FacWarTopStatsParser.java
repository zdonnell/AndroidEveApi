package com.zdonnell.androideveapi.eve.factwar.stats.top;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class FacWarTopStatsParser extends AbstractApiParser<FacWarTopStatsResponse> {
	private FacWarTopStatsParser() {
		super(FacWarTopStatsResponse.class, 2, ApiPath.EVE, ApiPage.FACT_WAR_TOP_STATS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new FacWarTopStatsHandler();
	}

	public static FacWarTopStatsParser getInstance() {
		return new FacWarTopStatsParser();
	}

	@Override
	public FacWarTopStatsResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}