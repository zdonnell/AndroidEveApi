package com.zdonnell.androideveapi.character.wallet.journal;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.wallet.journal.AbstractWalletJournalParser;
import com.zdonnell.androideveapi.shared.wallet.journal.WalletJournalResponse;

public class WalletJournalParser extends AbstractWalletJournalParser {
	private static final int DEFAULT_ROW_COUNT = 1000;

	private WalletJournalParser() {
		super(ApiPath.CHARACTER);
	}

	public static WalletJournalParser getInstance() {
		return new WalletJournalParser();
	}

	public WalletJournalResponse getWalletJournalResponse(ApiAuth<?> auth) throws ApiException {
		return getResponse(auth, 1000);
	}

	public WalletJournalResponse getWalletJournalResponse(ApiAuth<?> auth, long beforeRefID) throws ApiException {
		return getResponse(auth, 1000, beforeRefID, DEFAULT_ROW_COUNT);
	}

	public WalletJournalResponse getWalletJournalResponse(ApiAuth<?> auth, long beforeRefID, int rowCount)
			throws ApiException {
		return getResponse(auth, 1000, beforeRefID, rowCount);
	}

}