package com.zdonnell.androideveapi.link.eve;

import android.os.AsyncTask;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.conquerablestationlist.ConquerableStationListParser;
import com.zdonnell.androideveapi.eve.conquerablestationlist.StationListResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.IApiTask;

public class ConquerableStationsTask extends AsyncTask<Void, Void, StationListResponse> implements IApiTask<StationListResponse>
{
	private APIExceptionCallback<StationListResponse> callback;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
				
	public ConquerableStationsTask(APIExceptionCallback<StationListResponse> callback)
	{
		this.callback = callback;
		callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_NOT_FOUND);
	}
	
	@Override
	protected StationListResponse doInBackground(Void... params)
	{					
		ConquerableStationListParser parser = ConquerableStationListParser.getInstance();		
		StationListResponse response = null;
		
        try 
        { 
        	response = parser.getResponse();
        }
		catch (ApiException e) 
		{
			apiExceptionOccured = true;
			exception = e;
		}
        
        return response;
	}
	
	@Override
	protected void onPostExecute(StationListResponse response) 
	{
		if (apiExceptionOccured) 
		{
			callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
			callback.onError(response, exception);
		}
		else 
		{
			callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
			callback.onUpdate(response);
		}
    }
	
	public int requestTypeHash() 
	{
		return ApiPath.EVE.getPath().concat(ApiPage.CONQUERABLE_STATION_LIST.getPage()).hashCode();
	}

	public StationListResponse buildResponseFromDatabase() 
	{
		return null;
	}
}
