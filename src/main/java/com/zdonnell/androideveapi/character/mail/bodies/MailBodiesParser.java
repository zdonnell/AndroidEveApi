package com.zdonnell.androideveapi.character.mail.bodies;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.StringUtils;

public class MailBodiesParser extends AbstractListParser<MailBodiesHandler, MailBodiesResponse, ApiMailBody> {
	private MailBodiesParser() {
		super(MailBodiesResponse.class, 2, ApiPath.CHARACTER, ApiPage.MAIL_BODIES, MailBodiesHandler.class);
	}

	public static MailBodiesParser getInstance() {
		return new MailBodiesParser();
	}

	public MailBodiesResponse getResponse(ApiAuth<?> auth, long... ids) throws ApiException {
		return getResponse(auth, "ids", StringUtils.join(",", ids));
	}
}