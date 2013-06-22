package com.zdonnell.androideveapi.link.eve;

import android.content.Context;

import com.zdonnell.androideveapi.eve.conquerablestationlist.ConquerableStationListParser;
import com.zdonnell.androideveapi.eve.conquerablestationlist.StationListResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;

public class ConquerableStationsTask extends APITask<Void, Void, StationListResponse>
{		
	public ConquerableStationsTask(APIExceptionCallback<StationListResponse> callback, final Context context)
	{
		super(callback, context, false, null, new EveApiInteraction<StationListResponse>()
		{
			@Override
			public StationListResponse perform() throws ApiException 
			{
				ConquerableStationListParser parser = ConquerableStationListParser.getInstance();		
		        return parser.getResponse();
			}
		});
	}
	
	public int requestTypeHash() { return 0; /* cache not used */ }

	public StationListResponse buildResponseFromDatabase() { return null; /* cache not used */ }
}
