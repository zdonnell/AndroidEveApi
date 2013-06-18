package com.zdonnell.androideveapi.corporation.titles;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class CorporationTitlesParser extends AbstractListParser<CorporationTitlesHandler, CorporationTitlesResponse, ApiTitle> {
	public CorporationTitlesParser() {
		super(CorporationTitlesResponse.class, 2, ApiPath.CORPORATION, ApiPage.TITLES, CorporationTitlesHandler.class);
	}

	public static CorporationTitlesParser getInstance() {
		return new CorporationTitlesParser();
	}

	@Override
	public CorporationTitlesResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}