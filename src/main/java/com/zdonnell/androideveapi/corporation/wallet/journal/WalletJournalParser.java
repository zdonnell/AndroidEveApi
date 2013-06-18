package com.zdonnell.androideveapi.corporation.wallet.journal;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.wallet.journal.AbstractWalletJournalParser;

public class WalletJournalParser extends AbstractWalletJournalParser {
	private WalletJournalParser() {
		super(ApiPath.CORPORATION);
	}

	public static WalletJournalParser getInstance() {
		return new WalletJournalParser();
	}
}