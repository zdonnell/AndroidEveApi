package com.zdonnell.androideveapi.character.notifications.texts;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.StringUtils;

public class NotificationTextsParser extends AbstractListParser<NotificationTextsHandler, NotificationTextsResponse, ApiNotificationText> {
	private NotificationTextsParser() {
		super(NotificationTextsResponse.class, 2, ApiPath.CHARACTER, ApiPage.NOTIFICATION_TEXTS, NotificationTextsHandler.class);
	}

	public static NotificationTextsParser getInstance() {
		return new NotificationTextsParser();
	}

	public NotificationTextsResponse getResponse(ApiAuth<?> auth, long... notificationIDs) throws ApiException {
		return super.getResponse(auth, "ids", StringUtils.join(",", notificationIDs));
	}
}