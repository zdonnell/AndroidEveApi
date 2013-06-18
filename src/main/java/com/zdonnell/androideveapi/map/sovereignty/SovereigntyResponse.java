package com.zdonnell.androideveapi.map.sovereignty;

import java.util.HashMap;
import java.util.Map;

import com.zdonnell.androideveapi.core.ApiListResponse;

public class SovereigntyResponse extends ApiListResponse<ApiSystemSovereignty> {
	private static final long serialVersionUID = 1L;
	private final Map<Integer, ApiSystemSovereignty> systemSovereignties = new HashMap<Integer, ApiSystemSovereignty>();

	public void add(ApiSystemSovereignty systemSovereignty) {
		systemSovereignties.put(systemSovereignty.getSolarSystemID(), systemSovereignty);
		super.add(systemSovereignty);
	}

	public Map<Integer, ApiSystemSovereignty> getSystemSovereignties() {
		return systemSovereignties;
	}
}
