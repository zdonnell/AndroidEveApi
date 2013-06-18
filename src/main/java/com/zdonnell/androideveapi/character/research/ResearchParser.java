package com.zdonnell.androideveapi.character.research;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class ResearchParser extends AbstractListParser<ResearchHandler, ResearchResponse, ApiResearchAgent> {
	private ResearchParser() {
		super(ResearchResponse.class, 1, ApiPath.CHARACTER, ApiPage.RESEARCH, ResearchHandler.class);
	}

	public static ResearchParser getInstance() {
		return new ResearchParser();
	}

	@Override
	public ResearchResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}