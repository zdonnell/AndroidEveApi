package com.zdonnell.androideveapi.map.factwar.systems;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class FacWarSystemsParser extends AbstractListParser<FacWarSystemsHandler, FacWarSystemsResponse, ApiFacWarSystem> {
	private FacWarSystemsParser() {
		super(FacWarSystemsResponse.class, 2, ApiPath.MAP, ApiPage.FACTION_WAR_SYSTEMS, FacWarSystemsHandler.class);
	}
	
	public static FacWarSystemsParser getInstance() {
		return new FacWarSystemsParser();
	}

	@Override
	public FacWarSystemsResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}