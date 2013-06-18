package com.zdonnell.androideveapi.eve.conquerablestationlist;

import java.util.HashMap;
import java.util.Map;

import com.zdonnell.androideveapi.core.ApiResponse;

public class StationListResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;
	private final Map<Integer, ApiStation> stations = new HashMap<Integer, ApiStation>();

	public void add(ApiStation station) {
		stations.put(station.getStationID(), station);
	}

	public Map<Integer, ApiStation> getStations() {
		return stations;
	}
}
