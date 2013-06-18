package com.zdonnell.androideveapi.account.apikeyinfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.zdonnell.androideveapi.account.characters.EveCharacter;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.shared.KeyType;

public class ApiKeyInfoHandler extends AbstractContentHandler {
	private ApiKeyInfoResponse response;

	@Override
	public void startDocument() throws SAXException {
		response = new ApiKeyInfoResponse();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs)
			throws SAXException {
		if (qName.equals("key")) {
			response.setAccessMask(getLong(attrs, "accessMask"));
			response.setType(KeyType.valueOf(getString(attrs, "type")));
			String expires = attrs.getValue("expires").trim();
			if(expires.length() > 0)
				response.setExpires(getDate(expires));
		} else if (qName.equals("row")) {
			EveCharacter character = new EveCharacter();
			character.setCharacterID(getLong(attrs, "characterID"));
			character.setName(getString(attrs, "characterName"));
			character.setCorporationID(getLong(attrs, "corporationID"));
			character.setCorporationName(getString(attrs, "corporationName"));
			response.addEveCharacter(character);
		}
		super.startElement(uri, localName, qName, attrs);
		accumulator.setLength(0);
	}

	@Override
	public ApiKeyInfoResponse getResponse() {
		return response;
	}
}
