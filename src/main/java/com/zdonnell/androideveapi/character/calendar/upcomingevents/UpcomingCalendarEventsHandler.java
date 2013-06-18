package com.zdonnell.androideveapi.character.calendar.upcomingevents;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.character.calendar.CalendarEventResponse;
import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class UpcomingCalendarEventsHandler extends AbstractContentListHandler<UpcomingCalendarEventsResponse, EveUpcomingCalendarEvent> {

	public UpcomingCalendarEventsHandler() {
		super(UpcomingCalendarEventsResponse.class);
	}

	@Override
	protected EveUpcomingCalendarEvent getItem(Attributes attrs) {
		EveUpcomingCalendarEvent calendarEvent = new EveUpcomingCalendarEvent();
		calendarEvent.setEventID(getLong(attrs, "eventID"));
		calendarEvent.setOwnerID(getLong(attrs, "ownerID"));
		calendarEvent.setOwnerName(getString(attrs, "ownerName"));
		calendarEvent.setEventDate(getDate(attrs, "eventDate"));
		calendarEvent.setEventTitle(getString(attrs, "eventTitle"));
		calendarEvent.setDuration(getInt(attrs, "duration"));
		calendarEvent.setImportance(getInt(attrs, "importance"));
		calendarEvent.setResponse(CalendarEventResponse.valueOf(getString(attrs, "response").toUpperCase()));
		calendarEvent.setEventText(getString(attrs, "eventText"));
		return calendarEvent;
	}
}