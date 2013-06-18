package com.zdonnell.androideveapi.corporation.accountbalance;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.accountbalance.AbstractAccountBalanceParser;
import com.zdonnell.androideveapi.shared.accountbalance.AccountBalanceResponse;

public class AccountBalanceParser extends AbstractAccountBalanceParser {
	private AccountBalanceParser() {
		super(ApiPath.CORPORATION);
	}

	@Override
	public AccountBalanceResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
	
	public static AccountBalanceParser getInstance() {
		return new AccountBalanceParser();
	}
}