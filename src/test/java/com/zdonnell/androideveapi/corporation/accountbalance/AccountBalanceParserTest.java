package com.zdonnell.androideveapi.corporation.accountbalance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.accountbalance.AccountBalanceParser;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.accountbalance.AccountBalanceResponse;
import com.zdonnell.androideveapi.shared.accountbalance.EveAccountBalance;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class AccountBalanceParserTest extends FullAuthParserTest {
	public AccountBalanceParserTest() {
		super(ApiPath.CORPORATION, ApiPage.ACCOUNT_BALANCE);
	}

	@Test
	public void accountBalanceParser() throws ApiException {
		AccountBalanceParser parser = AccountBalanceParser.getInstance();
		AccountBalanceResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<EveAccountBalance> accountBalances = response.getAll();
		assertNotNull(accountBalances);
		assertEquals(7, accountBalances.size());
		boolean found = false;
		for (EveAccountBalance accountBalance : accountBalances) {
			if (accountBalance.getAccountID() == 5689) {
				found = true;
				assertEquals(1003, accountBalance.getAccountKey());
				assertEquals(17349111.00, accountBalance.getBalance(), 0.00001);
			}
		}
		assertTrue("test accountBalance wasn't found.", found);
	}
}