package com.zdonnell.androideveapi.corporation.contacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;


import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.contact.list.ContactListParser;
import com.zdonnell.androideveapi.corporation.contact.list.ContactListResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.contacts.ContactList;
import com.zdonnell.androideveapi.shared.contacts.EveContact;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class ContactListParserTest extends FullAuthParserTest {
	public ContactListParserTest() {
		super(ApiPath.CORPORATION, ApiPage.CONTACT_LIST);
	}

	@Test
	public void getResponse() throws ApiException {
		ContactListParser parser = ContactListParser.getInstance();
		ContactListResponse response = parser.getResponse(auth);
		assertNotNull(response);

		ContactList corporateContactList = response.getCorporateContactList();
		assertEquals("corporateContactList", corporateContactList.getName());
		assertEquals(19, corporateContactList.size());
		EveContact apiContact = corporateContactList.iterator().next();
		assertEquals(126118228, apiContact.getContactID());
		assertEquals("Alpha Guardians", apiContact.getContactName());
		assertEquals(10.0, apiContact.getStanding(), 1E-15);
		assertFalse(apiContact.isInWatchlist());

		ContactList allianceContactList = response.getAllianceContactList();
		assertEquals("allianceContactList", allianceContactList.getName());
		assertEquals(210, allianceContactList.size());
		apiContact = allianceContactList.iterator().next();
		assertEquals(121766272, apiContact.getContactID());
		assertEquals("X-COM", apiContact.getContactName());
		assertEquals(5.0, apiContact.getStanding(), 1E-15);
		assertFalse(apiContact.isInWatchlist());
	}
}