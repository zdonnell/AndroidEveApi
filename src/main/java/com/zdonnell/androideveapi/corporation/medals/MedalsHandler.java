package com.zdonnell.androideveapi.corporation.medals;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class MedalsHandler extends AbstractContentListHandler<CorpMedalsResponse, CorpMedal> {
	public MedalsHandler() {
		super(CorpMedalsResponse.class);
	}

	@Override
	protected CorpMedal getItem(Attributes attrs) {
		CorpMedal medal = new CorpMedal();
		medal.setMedalID(getInt(attrs, "medalID"));
		medal.setDescription(getString(attrs, "description"));
		medal.setCreatorID(getLong(attrs, "creatorID"));
		medal.setCreated(getDate(attrs, "created"));
		medal.setTitle(getString(attrs, "title"));
		return medal;
	}
}