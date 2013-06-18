package com.zdonnell.androideveapi.eve.conquerablestationlist;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class ConquerableStationListParser extends AbstractApiParser<StationListResponse> {
	public ConquerableStationListParser() {
		super(StationListResponse.class, 2, ApiPath.EVE, ApiPage.CONQUERABLE_STATION_LIST);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new ConquerableStationListHandler();
	}

	public static ConquerableStationListParser getInstance() {
		return new ConquerableStationListParser();
	}

	@Override
	public StationListResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}