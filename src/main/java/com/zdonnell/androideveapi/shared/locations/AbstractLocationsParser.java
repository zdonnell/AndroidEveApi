package com.zdonnell.androideveapi.shared.locations;

import java.util.Iterator;
import java.util.List;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractLocationsParser extends AbstractListParser<LocationsHandler, LocationsResponse, ApiLocation> {
	protected AbstractLocationsParser(ApiPath path) {
		super(LocationsResponse.class, 2, path, ApiPage.LOCATIONS, LocationsHandler.class);
	}

	public LocationsResponse getResponse(ApiAuth<?> auth, List<Long> itemIDs) throws ApiException {
		StringBuilder idList = new StringBuilder();
		for (Iterator<Long> iterator = itemIDs.iterator(); iterator.hasNext();) {
			idList.append((Long) iterator.next());
			if(iterator.hasNext())
				idList.append(",");
		}
		return super.getResponse(auth, "IDs", idList.toString());
	}
}