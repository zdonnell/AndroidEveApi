package com.zdonnell.androideveapi.character.calendar.upcomingevents;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class UpcomingCalendarEventsParser extends AbstractListParser<UpcomingCalendarEventsHandler, UpcomingCalendarEventsResponse, EveUpcomingCalendarEvent> {
	public UpcomingCalendarEventsParser() {
		super(UpcomingCalendarEventsResponse.class, 2, ApiPath.CHARACTER, ApiPage.UPCOMING_CALENDAR_EVENTS, UpcomingCalendarEventsHandler.class);
	}

	public static UpcomingCalendarEventsParser getInstance() {
		return new UpcomingCalendarEventsParser();
	}

	@Override
	public UpcomingCalendarEventsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}