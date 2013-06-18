package com.zdonnell.androideveapi.character.sheet;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class CharacterSheetParser extends AbstractApiParser<CharacterSheetResponse> {
	public CharacterSheetParser() {
		super(CharacterSheetResponse.class, 1, ApiPath.CHARACTER, ApiPage.CHARACTER_SHEET);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new CharacterSheetHandler();
	}

	public static CharacterSheetParser getInstance() {
		return new CharacterSheetParser();
	}

	@Override
	public CharacterSheetResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}