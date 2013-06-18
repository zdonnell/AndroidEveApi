package com.zdonnell.androideveapi.eve.alliancelist;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class AllianceListParser extends AbstractListParser<AllianceListHandler, AllianceListResponse, ApiAlliance> {
	public AllianceListParser() {
		super(AllianceListResponse.class, 2, ApiPath.EVE, ApiPage.ALLIANCE_LIST, AllianceListHandler.class);
	}

	public static AllianceListParser getInstance() {
		return new AllianceListParser();
	}

	@Override
	public AllianceListResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}