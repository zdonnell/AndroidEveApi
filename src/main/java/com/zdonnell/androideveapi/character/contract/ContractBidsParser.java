package com.zdonnell.androideveapi.character.contract;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contract.bids.AbstractContractBidsParser;

public class ContractBidsParser extends AbstractContractBidsParser {
	protected ContractBidsParser() {
		super(ApiPath.CHARACTER);
	}
	
	public static ContractBidsParser getInstance() {
		return new ContractBidsParser();
	}
}