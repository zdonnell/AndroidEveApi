package com.zdonnell.androideveapi.corporation.outpost.list;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class OutpostListParser extends AbstractListParser<OutpostListHandler, OutpostListResponse, ApiOutpost> {
	public OutpostListParser() {
		super(OutpostListResponse.class, 2, ApiPath.CORPORATION, ApiPage.OUTPOST_LIST, OutpostListHandler.class);
	}

	public static OutpostListParser getInstance() {
		return new OutpostListParser();
	}

	@Override
	public OutpostListResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}