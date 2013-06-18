package com.zdonnell.androideveapi.corporation.wallet.transactions;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.wallet.transactions.AbstractWalletTransactionsParser;

public class WalletTransactionsParser extends AbstractWalletTransactionsParser {
	private WalletTransactionsParser() {
		super(ApiPath.CORPORATION);
	}

	public static WalletTransactionsParser getInstance() {
		return new WalletTransactionsParser();
	}
}