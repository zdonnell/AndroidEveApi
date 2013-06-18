package com.zdonnell.androideveapi.shared.contacts;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractContactListParser<CLR extends AbstractContactListResponse> extends AbstractApiParser<CLR> {
	protected AbstractContactListParser(Class<CLR> responseClass, ApiPath path) {
		super(responseClass, 2, path, ApiPage.CONTACT_LIST);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new ContactListHandler<CLR>(clazz);
	}

	@Override
	public CLR getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}