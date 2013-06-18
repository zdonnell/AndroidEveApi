package com.zdonnell.androideveapi.shared.contract;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractContractsParser extends AbstractListParser<ContractsHandler, ContractsResponse, EveContract> {
	protected AbstractContractsParser(ApiPath path) {
		super(ContractsResponse.class, 2, path, ApiPage.CONTRACTS, ContractsHandler.class);
	}
	
	@Override
	public ContractsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}