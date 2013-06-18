package com.zdonnell.androideveapi.character.skill.intraining;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class SkillInTrainingParser extends AbstractApiParser<SkillInTrainingResponse> {
	public SkillInTrainingParser() {
		super(SkillInTrainingResponse.class, 2, ApiPath.CHARACTER, ApiPage.SKILL_IN_TRAINING);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new SkillInTrainingHandler();
	}

	public static SkillInTrainingParser getInstance() {
		return new SkillInTrainingParser();
	}

	@Override
	public SkillInTrainingResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}