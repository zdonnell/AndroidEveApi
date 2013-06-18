package com.zdonnell.androideveapi.character.contact.list;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.contacts.AbstractContactListParser;

public class ContactListParser extends AbstractContactListParser<ContactListResponse> {
	protected ContactListParser() {
		super(ContactListResponse.class, ApiPath.CHARACTER);
	}

	public static ContactListParser getInstance() {
		return new ContactListParser();
	}
}