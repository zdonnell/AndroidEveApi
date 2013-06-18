package com.zdonnell.androideveapi.character.contacts;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.character.contact.notifications.ContactNotificationsParser;
import com.zdonnell.androideveapi.character.contact.notifications.ContactNotificationsResponse;
import com.zdonnell.androideveapi.character.contact.notifications.EveContactNotification;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class ContactNotificationsParserTest extends FullAuthParserTest {
	public ContactNotificationsParserTest() {
		super(ApiPath.CHARACTER, ApiPage.CONTACT_NOTIFICATIONS);
	}

	@Test
	public void getResponse() throws ApiException {
		ContactNotificationsParser parser = ContactNotificationsParser.getInstance();
		ContactNotificationsResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<EveContactNotification> contactNotifications = response.getAll();
		assertEquals(1, contactNotifications.size());
		EveContactNotification contactNotification = contactNotifications.iterator().next();
		assertEquals(308734131, contactNotification.getNotificationID());
		assertEquals(797400947L, contactNotification.getSenderID());
		assertEquals("CCP Garthagk", contactNotification.getSenderName());
		assertDate(2010, 5, 29, 23, 4, 0, contactNotification.getSentDate());
		assertEquals("level: 10\nmessage: Hi, I want to social network with you!\n", contactNotification.getMessageData());
	}
}