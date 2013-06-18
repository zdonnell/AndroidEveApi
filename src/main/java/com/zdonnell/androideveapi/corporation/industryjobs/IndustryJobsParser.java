package com.zdonnell.androideveapi.corporation.industryjobs;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.industryjobs.AbstractIndustryJobsParser;

public class IndustryJobsParser extends AbstractIndustryJobsParser {
	private IndustryJobsParser() {
		super(ApiPath.CORPORATION);
	}

	public static IndustryJobsParser getInstance() {
		return new IndustryJobsParser();
	}
}