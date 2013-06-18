package com.zdonnell.androideveapi.character.mail.lists;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MailingListsParser extends AbstractListParser<MailingListsHandler, MailingListsResponse, ApiMailingList> {
	private MailingListsParser() {
		super(MailingListsResponse.class, 2, ApiPath.CHARACTER, ApiPage.MAILING_LISTS, MailingListsHandler.class);
	}

	public static MailingListsParser getInstance() {
		return new MailingListsParser();
	}

	@Override
	public MailingListsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}