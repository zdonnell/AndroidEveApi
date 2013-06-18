package com.zdonnell.androideveapi.eve.reftypes;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class RefTypesParser extends AbstractListParser<RefTypesHandler, RefTypesResponse, ApiRefType> {
	public RefTypesParser() {
		super(RefTypesResponse.class, 1, ApiPath.EVE, ApiPage.REF_TYPES, RefTypesHandler.class);
	}

	public static RefTypesParser getInstance() {
		return new RefTypesParser();
	}

	@Override
	public RefTypesResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}