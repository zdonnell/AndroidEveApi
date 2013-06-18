package com.zdonnell.androideveapi.shared.accountbalance;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;

public abstract class AbstractAccountBalanceParser extends AbstractListParser<AccountBalanceHandler, AccountBalanceResponse, EveAccountBalance> {
	protected AbstractAccountBalanceParser(ApiPath path) {
		super(AccountBalanceResponse.class, 2, path, ApiPage.ACCOUNT_BALANCE, AccountBalanceHandler.class);
	}
}