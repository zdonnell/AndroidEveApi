package com.zdonnell.androideveapi.shared.industryjobs;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractIndustryJobsParser extends AbstractListParser<IndustryJobsHandler, IndustryJobsResponse, ApiIndustryJob> {
	protected AbstractIndustryJobsParser(ApiPath path) {
		super(IndustryJobsResponse.class, 2, path, ApiPage.INDUSTRY_JOBS, IndustryJobsHandler.class);
	}

	@Override
	public IndustryJobsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}