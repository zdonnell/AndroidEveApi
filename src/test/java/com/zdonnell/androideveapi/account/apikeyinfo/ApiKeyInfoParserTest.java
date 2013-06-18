package com.zdonnell.androideveapi.account.apikeyinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.zdonnell.androideveapi.account.apikeyinfo.ApiKeyInfoParser;
import com.zdonnell.androideveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.zdonnell.androideveapi.account.characters.EveCharacter;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

import java.util.ArrayList;
import java.util.List;


public class ApiKeyInfoParserTest extends FullAuthParserTest {
	public ApiKeyInfoParserTest() {
		super(ApiPath.ACCOUNT, ApiPage.API_KEY_INFO);
	}

	@Test
	public void getResponse() throws Exception {
		ApiKeyInfoParser apiKeyInfoParser = ApiKeyInfoParser.getInstance();
		ApiKeyInfoResponse response = apiKeyInfoParser.getResponse(auth);
		assertNotNull(response);

		assertEquals(4227, response.getAccessMask());
		assertNull(response.getExpires());

		assertEquals(3, response.getEveCharacters().size());

		List<EveCharacter> characters = new ArrayList<EveCharacter>(response.getEveCharacters());

		assertEquals(987623974, characters.get(0).getCharacterID());
		assertEquals("Golden Gnu", characters.get(0).getName());
		assertEquals(1020386980, characters.get(0).getCorporationID());
		assertEquals("The Golden Gnu Corp", characters.get(0).getCorporationName());

		assertEquals(1652509239, characters.get(1).getCharacterID());
		assertEquals("GoldenGnu", characters.get(1).getName());
		assertEquals(1020386980, characters.get(1).getCorporationID());
		assertEquals("The Golden Gnu Corp", characters.get(1).getCorporationName());

		assertEquals(1683690353, characters.get(2).getCharacterID());
		assertEquals("Silver Gnu", characters.get(2).getName());
		assertEquals(1020386980, characters.get(2).getCorporationID());
		assertEquals("The Golden Gnu Corp", characters.get(2).getCorporationName());
		//assertEquals(5603, response.getCharacterID());
		//assertEquals(504903, response.getLogonMinutes());
	}
}