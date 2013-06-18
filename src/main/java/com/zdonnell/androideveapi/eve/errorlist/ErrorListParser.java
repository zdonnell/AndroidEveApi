package com.zdonnell.androideveapi.eve.errorlist;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class ErrorListParser extends AbstractListParser<ErrorListHandler, ErrorListResponse, ApiErrorListItem> {
	public ErrorListParser() {
		super(ErrorListResponse.class, 2, ApiPath.EVE, ApiPage.ERROR_LIST, ErrorListHandler.class);
	}

	public static ErrorListParser getInstance() {
		return new ErrorListParser();
	}

	@Override
	public ErrorListResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}