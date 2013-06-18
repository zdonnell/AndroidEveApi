package com.zdonnell.androideveapi.eve.character;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.character.ApiCharacterLookup;
import com.zdonnell.androideveapi.eve.character.CharacterLookupParser;
import com.zdonnell.androideveapi.eve.character.CharacterLookupResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.NoAuthParserTest;

public class CharacterIDLookupParserTest extends NoAuthParserTest {
	public CharacterIDLookupParserTest() {
		super(ApiPath.EVE, ApiPage.CHARACTER_ID);
	}

	@Test
	public void getResponse() throws ApiException {
		CharacterLookupParser parser = CharacterLookupParser.getName2IdInstance();
		CharacterLookupResponse response = parser.getResponse("CCP Garthagk");
		Collection<ApiCharacterLookup> chars = response.getAll();
		assertEquals(1, chars.size());
		ApiCharacterLookup garthagk = chars.iterator().next();
		assertEquals("CCP Garthagk", garthagk.getName());
		assertEquals(797400947, garthagk.getCharacterID());
	}

	@Override
	public void extraAsserts(Map<String, String> req) {
		super.extraAsserts(req);
		assertEquals("CCP Garthagk", req.get("names"));
	}
}