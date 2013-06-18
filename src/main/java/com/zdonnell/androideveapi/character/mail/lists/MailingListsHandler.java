package com.zdonnell.androideveapi.character.mail.lists;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class MailingListsHandler extends AbstractContentListHandler<MailingListsResponse, ApiMailingList> {

	public MailingListsHandler() {
		super(MailingListsResponse.class);
	}

	@Override
	protected ApiMailingList getItem(Attributes attrs) {
		ApiMailingList mailingList = new ApiMailingList();
		mailingList.setListID(getLong(attrs, "listID"));
		mailingList.setDisplayName(getString(attrs, "displayName"));
		return mailingList;
	}
}