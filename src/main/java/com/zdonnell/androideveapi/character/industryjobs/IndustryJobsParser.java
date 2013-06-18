package com.zdonnell.androideveapi.character.industryjobs;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.industryjobs.AbstractIndustryJobsParser;

public class IndustryJobsParser extends AbstractIndustryJobsParser {
	private IndustryJobsParser() {
		super(ApiPath.CHARACTER);
	}

	public static IndustryJobsParser getInstance() {
		return new IndustryJobsParser();
	}
}