package com.zdonnell.androideveapi.character.skill.queue;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class SkillQueueParser extends AbstractListParser<SkillQueueHandler, SkillQueueResponse, ApiSkillQueueItem> {
	public SkillQueueParser() {
		super(SkillQueueResponse.class, 2, ApiPath.CHARACTER, ApiPage.SKILL_QUEUE, SkillQueueHandler.class);
	}

	public static SkillQueueParser getInstance() {
		return new SkillQueueParser();
	}

	@Override
	public SkillQueueResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}