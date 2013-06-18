package com.zdonnell.androideveapi.account.apikeyinfo;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;


public class ApiKeyInfoParser extends AbstractApiParser<ApiKeyInfoResponse> {
	public ApiKeyInfoParser() {
		super(ApiKeyInfoResponse.class, 2, ApiPath.ACCOUNT, ApiPage.API_KEY_INFO);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new ApiKeyInfoHandler();
	}

	public static ApiKeyInfoParser getInstance() {
		return new ApiKeyInfoParser();
	}

	@Override
	public ApiKeyInfoResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}
