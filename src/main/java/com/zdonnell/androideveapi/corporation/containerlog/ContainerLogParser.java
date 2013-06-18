package com.zdonnell.androideveapi.corporation.containerlog;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class ContainerLogParser extends AbstractListParser<ContainerLogHandler, ContainerLogResponse, ApiContainerLog> {
	public ContainerLogParser() {
		super(ContainerLogResponse.class, 2, ApiPath.CORPORATION, ApiPage.CONTAINER_LOG, ContainerLogHandler.class);
	}

	public static ContainerLogParser getInstance() {
		return new ContainerLogParser();
	}

	@Override
	public ContainerLogResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}