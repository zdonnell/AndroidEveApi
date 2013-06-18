package com.zdonnell.androideveapi.character.medals;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MedalsParser extends AbstractListParser<MedalsHandler, CharacterMedalsResponse, CharacterMedal> {
	private MedalsParser() {
		super(CharacterMedalsResponse.class, 2, ApiPath.CHARACTER, ApiPage.MEDALS, MedalsHandler.class);
	}

	public static MedalsParser getInstance() {
		return new MedalsParser();
	}

	@Override
	public CharacterMedalsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}