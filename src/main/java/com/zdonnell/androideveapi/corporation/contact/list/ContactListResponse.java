package com.zdonnell.androideveapi.corporation.contact.list;

import com.zdonnell.androideveapi.shared.contacts.AbstractContactListResponse;
import com.zdonnell.androideveapi.shared.contacts.ContactList;

public class ContactListResponse extends AbstractContactListResponse {
	private static final long serialVersionUID = 1L;

	public ContactList getCorporateContactList() {
		return contactLists.get("corporateContactList");
	}

	public ContactList getAllianceContactList() {
		return contactLists.get("allianceContactList");
	}

}