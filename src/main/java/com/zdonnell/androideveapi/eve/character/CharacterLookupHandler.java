package com.zdonnell.androideveapi.eve.character;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class CharacterLookupHandler extends AbstractContentListHandler<CharacterLookupResponse, ApiCharacterLookup> {

	public CharacterLookupHandler() {
		super(CharacterLookupResponse.class);
	}

	@Override
	protected ApiCharacterLookup getItem(Attributes attrs) {
		ApiCharacterLookup lookup = new ApiCharacterLookup();
		lookup.setCharacterID(getLong(attrs, "characterID"));
		lookup.setName(getString(attrs, "name"));
		return lookup;
	}
}