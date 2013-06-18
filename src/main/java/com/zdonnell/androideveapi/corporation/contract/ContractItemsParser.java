package com.zdonnell.androideveapi.corporation.contract;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contract.items.AbstractContractItemsParser;

public class ContractItemsParser extends AbstractContractItemsParser {
	protected ContractItemsParser() {
		super(ApiPath.CORPORATION);
	}

	public static ContractItemsParser getInstance() {
		return new ContractItemsParser();
	}
}