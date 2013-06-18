package com.zdonnell.androideveapi.character.contract;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contract.AbstractContractsParser;

public class ContractsParser extends AbstractContractsParser {
	protected ContractsParser() {
		super(ApiPath.CHARACTER);
	}
	
	public static ContractsParser getInstance() {
		return new ContractsParser();
	}
}