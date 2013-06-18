package com.zdonnell.androideveapi.character.contract;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contract.items.AbstractContractItemsParser;

public class ContractItemsParser extends AbstractContractItemsParser {
	protected ContractItemsParser() {
		super(ApiPath.CHARACTER);
	}

	public static ContractItemsParser getInstance() {
		return new ContractItemsParser();
	}
}