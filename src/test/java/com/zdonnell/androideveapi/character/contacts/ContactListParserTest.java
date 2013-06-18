package com.zdonnell.androideveapi.character.contacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.junit.Test;


import com.zdonnell.androideveapi.character.contact.list.ContactListParser;
import com.zdonnell.androideveapi.character.contact.list.ContactListResponse;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.contacts.ContactList;
import com.zdonnell.androideveapi.shared.contacts.EveContact;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class ContactListParserTest extends FullAuthParserTest {
	public ContactListParserTest() {
		super(ApiPath.CHARACTER, ApiPage.CONTACT_LIST);
	}

	@Test
	public void getResponse() throws ApiException {
		ContactListParser parser = ContactListParser.getInstance();
		ContactListResponse response = parser.getResponse(auth);
		assertNotNull(response);

		ContactList contactList = response.getContactList();
		assertEquals("contactList", contactList.getName());
		assertEquals(106, contactList.size());
		EveContact apiContact = contactList.iterator().next();
		assertEquals(3008667, apiContact.getContactID());
		assertEquals("Falian Khivad", apiContact.getContactName());
		assertEquals(5.0, apiContact.getStanding(), 1E-15);
		assertFalse(apiContact.isInWatchlist());

		apiContact = contactList.get(contactList.size() - 1);
		assertEquals(2065725204, apiContact.getContactID());
		assertEquals("De Boer", apiContact.getContactName());
		assertEquals(10.0, apiContact.getStanding(), 1E-15);
		assertTrue(apiContact.isInWatchlist());
	}
}