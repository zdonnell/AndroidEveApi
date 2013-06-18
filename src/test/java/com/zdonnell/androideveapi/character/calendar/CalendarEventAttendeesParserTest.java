package com.zdonnell.androideveapi.character.calendar;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.character.calendar.CalendarEventResponse;
import com.zdonnell.androideveapi.character.calendar.attendees.CalendarEventAttendeesParser;
import com.zdonnell.androideveapi.character.calendar.attendees.CalendarEventAttendeesResponse;
import com.zdonnell.androideveapi.character.calendar.attendees.EveCalendarEventAttendee;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class CalendarEventAttendeesParserTest extends FullAuthParserTest {
	public CalendarEventAttendeesParserTest() {
		super(ApiPath.CHARACTER, ApiPage.CALENDAR_EVENT_ATTENDEES);
	}

	@Test
	public void getResponse() throws ApiException {
		CalendarEventAttendeesParser parser = CalendarEventAttendeesParser.getInstance();
		CalendarEventAttendeesResponse response = parser.getResponse(auth, 133918L);
		Set<EveCalendarEventAttendee> attendees = response.getAll();
		assertEquals(1, attendees.size());
		EveCalendarEventAttendee event = attendees.iterator().next();
		assertEquals(133918L, event.getEventID());
		assertEquals(1380128241L, event.getCharacterID());
		assertEquals("Zy'or Tealon", event.getCharacterName());
		assertEquals(CalendarEventResponse.ACCEPTED, event.getResponse());
	}

	@Override
	public void extraAsserts(Map<String, String> req) {
		super.extraAsserts(req);
		assertEquals("133918", req.get("eventIDs"));
	}
}