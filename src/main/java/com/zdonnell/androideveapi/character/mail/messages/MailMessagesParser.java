package com.zdonnell.androideveapi.character.mail.messages;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MailMessagesParser extends AbstractListParser<MailMessagesHandler, MailMessagesResponse, ApiMailMessage> {
	private MailMessagesParser() {
		super(MailMessagesResponse.class, 2, ApiPath.CHARACTER, ApiPage.MAIL_MESSAGES, MailMessagesHandler.class);
	}

	public static MailMessagesParser getInstance() {
		return new MailMessagesParser();
	}

	@Override
	public MailMessagesResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}