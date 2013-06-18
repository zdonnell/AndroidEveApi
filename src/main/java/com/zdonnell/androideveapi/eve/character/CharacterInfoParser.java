package com.zdonnell.androideveapi.eve.character;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class CharacterInfoParser extends AbstractApiParser<CharacterInfoResponse> {
	public CharacterInfoParser() {
		super(CharacterInfoResponse.class, 2, ApiPath.EVE, ApiPage.CHARACTER_INFO);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new CharacterInfoHandler();
	}

	public static CharacterInfoParser getInstance() {
		return new CharacterInfoParser();
	}

	public CharacterInfoResponse getResponse(long characterID) throws ApiException {
		return super.getResponse("characterID", Long.toString(characterID));
	}

	@Override
	public CharacterInfoResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}