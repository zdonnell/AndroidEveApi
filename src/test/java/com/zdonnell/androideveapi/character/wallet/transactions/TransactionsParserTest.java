package com.zdonnell.androideveapi.character.wallet.transactions;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.character.wallet.transactions.WalletTransactionsParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.wallet.transactions.AbstractWalletTransactionsParser;
import com.zdonnell.androideveapi.shared.wallet.transactions.ApiWalletTransaction;
import com.zdonnell.androideveapi.shared.wallet.transactions.WalletTransactionsResponse;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class TransactionsParserTest extends FullAuthParserTest {
	public TransactionsParserTest() {
		super(ApiPath.CHARACTER, ApiPage.WALLET_TRANSACTIONS);
	}

	@Test
	public void getResponse() throws ApiException {
		AbstractWalletTransactionsParser parser = WalletTransactionsParser.getInstance();
		WalletTransactionsResponse response = parser.getResponse(auth, 1000);
		assertNotNull(response);
		Set<ApiWalletTransaction> walletTransactions = response.getAll();
		assertEquals(25, walletTransactions.size());
		boolean found = false;
		for (ApiWalletTransaction walletTransaction : walletTransactions) {
			if (walletTransaction.getTransactionID() == 1364611263) {
				found = true;
				assertDate(2010, 3, 24, 19, 58, 0, walletTransaction.getTransactionDateTime());
				assertEquals(1000, walletTransaction.getQuantity());
				assertEquals("Tungsten Charge M", walletTransaction.getTypeName());
				assertEquals(224, walletTransaction.getTypeID());
				assertEquals(25.11, walletTransaction.getPrice(), 0.00001);
				assertEquals(696416951L, walletTransaction.getClientID());
				assertEquals("OMG Junior", walletTransaction.getClientName());
				assertEquals(60012667, walletTransaction.getStationID());
				assertEquals("Osmon II - Moon 1 - Sisters of EVE Bureau", walletTransaction.getStationName());
				assertEquals("buy", walletTransaction.getTransactionType());
				assertEquals("personal", walletTransaction.getTransactionFor());
			}
		}
		assertTrue("test order wasn't found.", found);
	}

	@Override
	public void extraAsserts(Map<String, String> req) {
		super.extraAsserts(req);
		assertEquals("1000", req.get("accountKey"));
	}
}