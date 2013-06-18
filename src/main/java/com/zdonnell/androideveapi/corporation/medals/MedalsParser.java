package com.zdonnell.androideveapi.corporation.medals;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MedalsParser extends AbstractListParser<MedalsHandler, CorpMedalsResponse, CorpMedal> {
	private MedalsParser() {
		super(CorpMedalsResponse.class, 2, ApiPath.CORPORATION, ApiPage.MEDALS, MedalsHandler.class);
	}

	public static MedalsParser getInstance() {
		return new MedalsParser();
	}

	@Override
	public CorpMedalsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}