package com.zdonnell.androideveapi.eve.skilltree;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class SkillTreeParser extends AbstractListParser<SkillTreeHandler, SkillTreeResponse, ApiSkillGroup> {
	public SkillTreeParser() {
		super(SkillTreeResponse.class, 2, ApiPath.EVE, ApiPage.SKILL_TREE, SkillTreeHandler.class);
	}

	public static SkillTreeParser getInstance() {
		return new SkillTreeParser();
	}

	@Override
	public SkillTreeResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}