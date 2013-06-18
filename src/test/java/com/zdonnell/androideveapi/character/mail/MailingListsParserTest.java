package com.zdonnell.androideveapi.character.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.character.mail.lists.ApiMailingList;
import com.zdonnell.androideveapi.character.mail.lists.MailingListsParser;
import com.zdonnell.androideveapi.character.mail.lists.MailingListsResponse;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class MailingListsParserTest extends FullAuthParserTest {
	public MailingListsParserTest() {
		super(ApiPath.CHARACTER, ApiPage.MAILING_LISTS);
	}

	@Test
	public void getResponse() throws ApiException {
		MailingListsParser parser = MailingListsParser.getInstance();
		MailingListsResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<ApiMailingList> mailinglists = response.getAll();
		assertNotNull(mailinglists);
		assertEquals(3, mailinglists.size());
		boolean found = false;
		for (ApiMailingList mailinglist : mailinglists) {
			if (mailinglist.getListID() == 128250439L) {
				found = true;
				assertEquals("EVETycoonMail", mailinglist.getDisplayName());
				break;
			}
		}
		assertTrue("Test mail wasn't found.", found);
	}
}