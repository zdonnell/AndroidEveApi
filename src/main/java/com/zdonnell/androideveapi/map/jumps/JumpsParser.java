package com.zdonnell.androideveapi.map.jumps;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class JumpsParser extends AbstractListParser<JumpsHandler, JumpsResponse, ApiSystemJumps> {
	public JumpsParser() {
		super(JumpsResponse.class, 2, ApiPath.MAP, ApiPage.JUMPS, JumpsHandler.class);
	}

	public static JumpsParser getInstance() {
		return new JumpsParser();
	}

	@Override
	public JumpsResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}