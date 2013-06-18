package com.zdonnell.androideveapi.corporation.locations;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.locations.AbstractLocationsParser;

public class LocationsParser extends AbstractLocationsParser {
	private LocationsParser() {
		super(ApiPath.CORPORATION);
	}

	public static LocationsParser getInstance() {
		return new LocationsParser();
	}
}