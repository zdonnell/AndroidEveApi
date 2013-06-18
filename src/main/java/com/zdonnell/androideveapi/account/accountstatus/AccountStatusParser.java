package com.zdonnell.androideveapi.account.accountstatus;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class AccountStatusParser extends AbstractApiParser<AccountStatusResponse> {
	public AccountStatusParser() {
		super(AccountStatusResponse.class, 2, ApiPath.ACCOUNT, ApiPage.ACCOUNT_STATUS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new AccountStatusHandler();
	}

	public static AccountStatusParser getInstance() {
		return new AccountStatusParser();
	}

	@Override
	public AccountStatusResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}