package com.zdonnell.androideveapi.corporation.contract;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contract.bids.AbstractContractBidsParser;

public class ContractBidsParser extends AbstractContractBidsParser {
	protected ContractBidsParser() {
		super(ApiPath.CORPORATION);
	}
	
	public static ContractBidsParser getInstance() {
		return new ContractBidsParser();
	}
}