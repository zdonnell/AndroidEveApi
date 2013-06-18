package com.zdonnell.androideveapi.character.contact.list;

import com.zdonnell.androideveapi.shared.contacts.AbstractContactListResponse;
import com.zdonnell.androideveapi.shared.contacts.ContactList;

public class ContactListResponse extends AbstractContactListResponse {
	private static final long serialVersionUID = 1L;

	public ContactList getContactList() {
		return contactLists.get("contactList");
	}
}