package com.zdonnell.androideveapi.link;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiResponse;
import com.zdonnell.androideveapi.exception.ApiException;

/**
 * Base AsyncTask for acquiring API Data. This class does not handle caching of
 * data.
 * 
 * @author Zach
 * 
 * @param <ExecuteParameter>
 * @param <ProgressParameter>
 * @param <Response>
 * 
 * @see ApiCachingTask
 * @see ApiSerialTask
 */
public abstract class ApiTask<ExecuteParameter, ProgressParameter, Response extends ApiResponse> extends AsyncTask<ExecuteParameter, ProgressParameter, Response> {
	/**
	 * Callback to provide the the acquired response to.
	 */
	protected final ApiExceptionCallback<Response> callback;

	/**
	 * Code to run to generate the response, provided by subclass
	 */
	final private EveApiInteraction<Response> apiInteraction;

	/**
	 * Flags whether an exception occurred when the {@link #apiInteraction} was
	 * run
	 */
	protected boolean apiExceptionOccured = false;

	/**
	 * Stores the ApiException if one occurs in {@link #apiInteraction}
	 */
	protected ApiException exception;

	/**
	 * Application context
	 */
	final protected Context context;

	/**
	 * Reference to the apiAuthorization used
	 */
	protected final ApiAuth<?> apiAuth;

	public ApiTask(ApiExceptionCallback<Response> callback, Context context, boolean useCache, ApiAuth<?> apiAuth, EveApiInteraction<Response> apiInteraction) {
		this.callback = callback;
		this.apiInteraction = apiInteraction;
		this.context = context;
		this.apiAuth = apiAuth;

		if(!(this instanceof ApiCachingTask))
			callback.updateState(ApiExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
	}

	@Override
	protected Response doInBackground(ExecuteParameter... params) {
		Response response = null;

		try {
			response = apiInteraction.perform();
		} catch(ApiException e) {
			apiExceptionOccured = true;
			exception = e;
		}
		return response;
	}

	@Override
	protected void onPostExecute(Response response) {
		if(apiExceptionOccured) {
			callback.updateState(ApiExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
			callback.onError(response, exception);
		} else {
			callback.updateState(ApiExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
			callback.onUpdate(response);
		}
	}

	/**
	 * Interface to be implemented for interaction with the eveapi data specific
	 * to the current request.
	 * 
	 * @author Zach
	 * 
	 * @param <Response>
	 * @see {@link ApiTask#apiInteraction}
	 */
	protected interface EveApiInteraction<Response> {
		Response perform() throws ApiException;
	}

}