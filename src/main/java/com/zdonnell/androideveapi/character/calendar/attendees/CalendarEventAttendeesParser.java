package com.zdonnell.androideveapi.character.calendar.attendees;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.StringUtils;

public class CalendarEventAttendeesParser extends AbstractListParser<CalendarEventAttendeesHandler, CalendarEventAttendeesResponse, EveCalendarEventAttendee> {
	public CalendarEventAttendeesParser() {
		super(CalendarEventAttendeesResponse.class, 2, ApiPath.CHARACTER, ApiPage.CALENDAR_EVENT_ATTENDEES, CalendarEventAttendeesHandler.class);
	}

	public static CalendarEventAttendeesParser getInstance() {
		return new CalendarEventAttendeesParser();
	}

	public CalendarEventAttendeesResponse getResponse(ApiAuth<?> auth, long... eventIDs) throws ApiException {
		if (eventIDs.length == 0)
			throw new ApiException("no eventIds provided");
		return super.getResponse(auth, "eventIDs", StringUtils.join(",", eventIDs));
	}
}