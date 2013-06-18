package com.zdonnell.androideveapi.character.accountbalance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.character.accountbalance.AccountBalanceParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.accountbalance.AccountBalanceResponse;
import com.zdonnell.androideveapi.shared.accountbalance.EveAccountBalance;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class AccountBalanceParserTest extends FullAuthParserTest {
	public AccountBalanceParserTest() {
		super(ApiPath.CHARACTER, ApiPage.ACCOUNT_BALANCE);
	}

	@Test
	public void getResponse() throws ApiException {
		AccountBalanceParser parser = AccountBalanceParser.getInstance();
		AccountBalanceResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<EveAccountBalance> accountBalances = response.getAll();
		assertNotNull(accountBalances);
		assertEquals(1, accountBalances.size());
		EveAccountBalance accountBalance = accountBalances.iterator().next();
		assertEquals(10094361, accountBalance.getAccountID());
		assertEquals(1000, accountBalance.getAccountKey());
		assertEquals(46634844.84, accountBalance.getBalance(), 0.00001);
	}
}