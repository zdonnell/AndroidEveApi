package com.zdonnell.androideveapi.corporation.containerlog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.containerlog.ApiContainerLog;
import com.zdonnell.androideveapi.corporation.containerlog.ContainerLogParser;
import com.zdonnell.androideveapi.corporation.containerlog.ContainerLogResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class ContainerLogParserTest extends FullAuthParserTest {
	public ContainerLogParserTest() {
		super(ApiPath.CORPORATION, ApiPage.CONTAINER_LOG);
	}

	@Test
	public void getResponse() throws ApiException {
		ContainerLogParser parser = ContainerLogParser.getInstance();
		ContainerLogResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<ApiContainerLog> containerLogs = response.getAll();
		assertNotNull(containerLogs);
		assertEquals(23, containerLogs.size());
	}
}