package com.zdonnell.androideveapi.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;


import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.server.ServerStatusParser;
import com.zdonnell.androideveapi.server.ServerStatusResponse;
import com.zdonnell.androideveapi.utils.NoAuthParserTest;

public class ServerStatusParserTest extends NoAuthParserTest {
	public ServerStatusParserTest() {
		super(ApiPath.SERVER, ApiPage.SERVER_STATUS);
	}

	@Test
	public void getResponse() throws ApiException {
		ServerStatusParser parser = ServerStatusParser.getInstance();
		ServerStatusResponse response = parser.getServerStatus();
		assertNotNull(response);
		assertEquals(38669, response.getOnlinePlayers());
		assertEquals(true, response.isServerOpen());
	}
}