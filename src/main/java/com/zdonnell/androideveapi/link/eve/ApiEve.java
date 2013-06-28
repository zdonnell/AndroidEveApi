package com.zdonnell.androideveapi.link.eve;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.eve.conquerablestationlist.StationListResponse;
import com.zdonnell.androideveapi.eve.reftypes.RefTypesResponse;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeResponse;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;

public class ApiEve 
{
	private Context context;
	
	public ApiEve(Context context)
	{
		this.context = context;
	}
	
	public void skillTree(ApiExceptionCallback<SkillTreeResponse> callback)
	{
		new SkillTreeTask(callback, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
	
	public void conqStationsList(ApiExceptionCallback<StationListResponse> callback)
	{
		new ConquerableStationsTask(callback, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
	
	public void refTypes(ApiExceptionCallback<RefTypesResponse> callback)
	{
		new RefTypesTask(callback, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
}
