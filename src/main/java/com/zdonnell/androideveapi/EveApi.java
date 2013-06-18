package com.zdonnell.androideveapi;

import java.util.Set;

import com.zdonnell.androideveapi.account.accountstatus.AccountStatusParser;
import com.zdonnell.androideveapi.account.accountstatus.EveAccountStatus;
import com.zdonnell.androideveapi.account.apikeyinfo.ApiKeyInfoParser;
import com.zdonnell.androideveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.zdonnell.androideveapi.account.characters.CharactersParser;
import com.zdonnell.androideveapi.account.characters.EveCharacter;
import com.zdonnell.androideveapi.api.calllist.CallList;
import com.zdonnell.androideveapi.api.calllist.CallListParser;
import com.zdonnell.androideveapi.character.calendar.attendees.CalendarEventAttendeesParser;
import com.zdonnell.androideveapi.character.calendar.attendees.EveCalendarEventAttendee;
import com.zdonnell.androideveapi.character.calendar.upcomingevents.EveUpcomingCalendarEvent;
import com.zdonnell.androideveapi.character.calendar.upcomingevents.UpcomingCalendarEventsParser;
import com.zdonnell.androideveapi.character.contact.notifications.ContactNotificationsParser;
import com.zdonnell.androideveapi.character.contact.notifications.EveContactNotification;
import com.zdonnell.androideveapi.connectors.ApiConnector;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.exception.NoAuthException;
import com.zdonnell.androideveapi.shared.KeyType;
import com.zdonnell.androideveapi.shared.accountbalance.AccountBalanceResponse;
import com.zdonnell.androideveapi.shared.accountbalance.EveAccountBalance;
import com.zdonnell.androideveapi.shared.assetlist.EveAsset;
import com.zdonnell.androideveapi.shared.contacts.ContactList;

public class EveApi {
	private static ApiConnector connector = new ApiConnector();
	private ApiAuth<?> auth;

	public EveApi() {
		// default constructor
	}

	public EveApi(ApiAuth<?> auth) {
		this.auth = auth;
	}

	public ApiAuth<?> getAuth() {
		if (auth == null)
			throw new NoAuthException();
		return auth;
	}

	public void setAuth(ApiAuth<?> auth) {
		this.auth = auth;
	}

	public static ApiConnector getConnector() {
		return connector.getInstance();
	}

	public static void setConnector(ApiConnector connector) {
		EveApi.connector = connector;
	}

	public ApiKeyInfoResponse getAPIKeyInfo() throws ApiException {
		ApiKeyInfoParser apiKeyInfoParser = ApiKeyInfoParser.getInstance();
		return apiKeyInfoParser.getResponse(auth);
	}

	public EveAccountStatus getAccountStatus() throws ApiException {
		return AccountStatusParser.getInstance().getResponse(getAuth()).get();
	}

	public Set<EveCharacter> getCharacters() throws ApiException {
		return CharactersParser.getInstance().getResponse(getAuth()).getAll();
	}

	public void selectCharacter(EveCharacter eveCharacter) {
		if (auth == null)
			throw new NoAuthException();
		auth.setCharacterID(eveCharacter.getCharacterID());
	}

	public void selectCharacter(long characterID) {
		if (auth == null)
			throw new NoAuthException();
		auth.setCharacterID(characterID);
	}

	public Set<EveAccountBalance> getAccountBalance(KeyType type) throws ApiException {
		AccountBalanceResponse response;
		if (type == KeyType.Character) {
			response = com.zdonnell.androideveapi.character.accountbalance.AccountBalanceParser.getInstance().getResponse(
					getAuth());
		} else if (type == KeyType.Corporation)
			response = com.zdonnell.androideveapi.corporation.accountbalance.AccountBalanceParser.getInstance().getResponse(
					getAuth());
		else
			return null;
		if (response.hasError())
			throw new ApiException(response.getError().getError());
		return response.getAll();
	}

	public Set<EveAsset<EveAsset<?>>> getCharacterAssets() throws ApiException {
		return com.zdonnell.androideveapi.character.assetlist.AssetListParser.getInstance().getResponse(getAuth()).getAll();
	}

	public Set<EveUpcomingCalendarEvent> getUpcomingCalendarEvents() throws ApiException {
		return UpcomingCalendarEventsParser.getInstance().getResponse(getAuth()).getAll();
	}

	public Set<EveCalendarEventAttendee> getCalendarEventAttendees(long... eventIds) throws ApiException {
		return CalendarEventAttendeesParser.getInstance().getResponse(getAuth(), eventIds).getAll();
	}

	public ContactList getContactList() throws ApiException {
		return com.zdonnell.androideveapi.character.contact.list.ContactListParser.getInstance().getResponse(getAuth())
				.getContactList();
	}

	public Set<EveContactNotification> getContactNotifications() throws ApiException {
		return ContactNotificationsParser.getInstance().getResponse(getAuth()).getAll();
	}

	public CallList getCallList() throws ApiException {
		return CallListParser.getInstance().getResponse().get();
	}
}