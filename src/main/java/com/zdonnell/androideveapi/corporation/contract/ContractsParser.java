package com.zdonnell.androideveapi.corporation.contract;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contract.AbstractContractsParser;

public class ContractsParser extends AbstractContractsParser {
	protected ContractsParser() {
		super(ApiPath.CORPORATION);
	}
	
	public static ContractsParser getInstance() {
		return new ContractsParser();
	}
}