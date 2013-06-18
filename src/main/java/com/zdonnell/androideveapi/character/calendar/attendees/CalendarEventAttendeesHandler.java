package com.zdonnell.androideveapi.character.calendar.attendees;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.character.calendar.CalendarEventResponse;
import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class CalendarEventAttendeesHandler extends AbstractContentListHandler<CalendarEventAttendeesResponse, EveCalendarEventAttendee> {
	public CalendarEventAttendeesHandler() {
		super(CalendarEventAttendeesResponse.class);
	}

	@Override
	protected EveCalendarEventAttendee getItem(Attributes attrs) {
		EveCalendarEventAttendee eventAttendee = new EveCalendarEventAttendee();
		eventAttendee.setEventID(getLong(attrs, "eventID"));
		eventAttendee.setCharacterID(getLong(attrs, "characterID"));
		eventAttendee.setCharacterName(getString(attrs, "characterName"));
		eventAttendee.setResponse(CalendarEventResponse.valueOf(getString(attrs, "response").toUpperCase()));
		return eventAttendee;
	}
}