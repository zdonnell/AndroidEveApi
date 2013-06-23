package com.zdonnell.androideveapi.link.eve;

import android.content.Context;

import com.zdonnell.androideveapi.eve.conquerablestationlist.ConquerableStationListParser;
import com.zdonnell.androideveapi.eve.conquerablestationlist.StationListResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.ApiTask;

public class ConquerableStationsTask extends ApiTask<Void, Void, StationListResponse> {		
	public ConquerableStationsTask(ApiExceptionCallback<StationListResponse> callback, final Context context) {
		super(callback, context, false, null, new EveApiInteraction<StationListResponse>() {
			@Override
			public StationListResponse perform() throws ApiException {
				ConquerableStationListParser parser = ConquerableStationListParser.getInstance();		
		        return parser.getResponse();
			}
		});
	}
}
