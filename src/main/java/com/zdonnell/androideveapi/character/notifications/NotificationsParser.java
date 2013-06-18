package com.zdonnell.androideveapi.character.notifications;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class NotificationsParser extends AbstractListParser<NotificationsHandler, NotificationsResponse, ApiNotification> {
	private NotificationsParser() {
		super(NotificationsResponse.class, 2, ApiPath.CHARACTER, ApiPage.NOTIFICATIONS, NotificationsHandler.class);
	}

	public static NotificationsParser getInstance() {
		return new NotificationsParser();
	}

	@Override
	public NotificationsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}