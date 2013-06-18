package com.zdonnell.androideveapi.account.apikeyinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.zdonnell.androideveapi.account.characters.EveCharacter;
import com.zdonnell.androideveapi.core.ApiResponse;
import com.zdonnell.androideveapi.shared.KeyType;

public class ApiKeyInfoResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;
	private long accessMask;
	private KeyType type;
	private Date expires;
	private final Collection<EveCharacter> eveCharacters = new ArrayList<EveCharacter>();

	public void addEveCharacter(EveCharacter eveCharacter) {
		eveCharacters.add(eveCharacter);
	}

	public Collection<EveCharacter> getEveCharacters() {
		return eveCharacters;
	}

	public long getAccessMask() {
		return accessMask;
	}

	public void setAccessMask(long accessMask) {
		this.accessMask = accessMask;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public KeyType getType() {
		return type;
	}

	public void setType(KeyType type) {
		this.type = type;
	}

	public boolean isCorporationKey() {
		return getType() == KeyType.Corporation;
	}

	public boolean isAccountKey() {
		return getType() == KeyType.Account;
	}

	public boolean isCharacterKey() {
		return getType() == KeyType.Character;
	}
}