package com.zdonnell.androideveapi.corporation.starbase.list;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class StarbaseListParser extends AbstractListParser<StarbaseListHandler, StarbaseListResponse, ApiStarbase> {
	public StarbaseListParser() {
		super(StarbaseListResponse.class, 2, ApiPath.CORPORATION, ApiPage.STARBASE_LIST, StarbaseListHandler.class);
	}

	public static StarbaseListParser getInstance() {
		return new StarbaseListParser();
	}

	@Override
	public StarbaseListResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}