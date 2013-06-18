package com.zdonnell.androideveapi.account.accountstatus;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.zdonnell.androideveapi.account.accountstatus.AccountStatusParser;
import com.zdonnell.androideveapi.account.accountstatus.AccountStatusResponse;
import com.zdonnell.androideveapi.account.accountstatus.EveAccountStatus;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class AccountStatusParserTest extends FullAuthParserTest {
	public AccountStatusParserTest() {
		super(ApiPath.ACCOUNT, ApiPage.ACCOUNT_STATUS);
	}

	@Test
	public void getResponse() throws Exception {
		AccountStatusParser accountStatusParser = AccountStatusParser.getInstance();
		AccountStatusResponse response = accountStatusParser.getResponse(auth);
		assertNotNull(response);
		EveAccountStatus accountStatus = response.get();
		assertEquals(541354, accountStatus.getUserID());
		assertDate(2011, 03, 13, 18, 40, 0, accountStatus.getPaidUntil());
		assertDate(2004, 07, 22, 23, 54, 0, accountStatus.getCreateDate());
		assertEquals(5603, accountStatus.getLogonCount());
		assertEquals(504903, accountStatus.getLogonMinutes());
	}
}