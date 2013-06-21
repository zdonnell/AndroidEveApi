package com.zdonnell.androideveapi.link.eve;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.eve.conquerablestationlist.StationListResponse;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeResponse;
import com.zdonnell.androideveapi.link.APIExceptionCallback;

public class Eve 
{
	private Context context;
	
	public Eve(Context context)
	{
		this.context = context;
	}
	
	public void skillTree(APIExceptionCallback<SkillTreeResponse> callback)
	{
		new SkillTreeTask(callback, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
	
	public void conqStationsList(APIExceptionCallback<StationListResponse> callback)
	{
		new ConquerableStationsTask(callback, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
}
