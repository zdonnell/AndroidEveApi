package com.zdonnell.androideveapi.shared.contract.bids;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractContractBidsParser extends AbstractListParser<ContractIBidsHandler, ContractBidsResponse, EveContractBid> {
	protected AbstractContractBidsParser(ApiPath path) {
		super(ContractBidsResponse.class, 2, path, ApiPage.CONTRACT_BIDS, ContractIBidsHandler.class);
	}

	@Override
	public ContractBidsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}