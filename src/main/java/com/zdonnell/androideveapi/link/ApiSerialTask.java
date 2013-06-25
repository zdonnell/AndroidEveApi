package com.zdonnell.androideveapi.link;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiListResponse;
import com.zdonnell.androideveapi.exception.ApiException;

/**
 * Base Task for ApiResponses that support entry walking.  
 * 
 * @author Zach
 *
 * @param <ExecuteParameter>
 * @param <ProgressParameter>
 * @param <Response>
 */
public abstract class ApiSerialTask<ExecuteParameter, ProgressParameter, Response extends ApiListResponse<?>> extends AsyncTask<ExecuteParameter, ProgressParameter, Response> {
	/**
	 * Callback to provide the the acquired response to.
	 */
	protected final ApiExceptionCallback<Response> callback;
	
	/**
	 * Flags whether an exception occurred when the {@link #apiInteraction} was run
	 */
	private boolean apiExceptionOccured = false;
	
	/**
	 * Stores the ApiException if one occurs in {@link #apiInteraction}
	 */
	private ApiException exception;
	
	/**
	 * Application context
	 */
	final protected Context context;
	
	/**
	 * Reference to the apiAuthorization used
	 */
	protected final ApiAuth<?> apiAuth;
	
	/**
	 * Number of rows to grab each iteration.
	 */
	protected final int batchSize;
	
	/**
	 * Response that will ultimately store the entire set of entries
	 */
	private Response finalResponse;
	
	public ApiSerialTask(ApiExceptionCallback<Response> callback, Context context, ApiAuth<?> apiAuth, int batchSize) {
		this.callback = callback;
		this.context = context;
		this.apiAuth = apiAuth;
		this.batchSize = batchSize;
		
		callback.updateState(ApiExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Response doInBackground(ExecuteParameter... params) {	
		long newestRefID = getNewestRefID();
		
		// There are at least some entries for this character, update the UI
		// with the old stuff while we wait for the new to load from the server
		if (newestRefID != 0) {
			finalResponse = buildResponseFromDatabase();
			publishProgress();
        	
			try { 	  
				checkForNewerEntries(finalResponse, newestRefID);
	        } catch (ApiException e) {
				apiExceptionOccured = true;
				exception = e;
			}
		} else { // There are no existing entries, start from scratch and request all available journal entries
	        try { 	        	
	        	finalResponse = getAllEntries();
	        } catch (ApiException e) {
				apiExceptionOccured = true;
				exception = e;
			}
		}
		
        return finalResponse;
	}
	
	@Override
	protected void onProgressUpdate(ProgressParameter... params) {
		callback.onUpdate(finalResponse);
	}
	
	@Override
	protected void onPostExecute(Response response) 
	{
		if (apiExceptionOccured) {
			callback.updateState(ApiExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
			callback.onError(response, exception);
		} else {
			callback.updateState(ApiExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
			callback.onUpdate(response);
		}
    }
	
	/**
	 * Subclasses implement this to provide the newest entry ID.
	 * 
	 * @return the newest entry ID or 0 if there are no current entries
	 */
	protected abstract long getNewestRefID();
	
	/**
	 * Subclasses implement this to provide a Response built from the database
	 * 
	 * @return
	 */
	protected abstract Response buildResponseFromDatabase();
	
	/**
	 * Makes a request to obtain all response entries newer than the provided newestRefID.  Subclass
	 * implementations should honor newestRefID and not request more entries than necessary.
	 * 
	 * @param finalResponse passed in ApiResposne that should be filled with any found newer entries.
	 * @param newestRefID 
	 * @throws ApiException
	 */
	protected abstract void checkForNewerEntries(Response finalResponse, long newestRefID) throws ApiException;
	
	/**
	 * Obtains all entries from the server.
	 * 
	 * @return A built Resposne object containing all entries found
	 * @throws ApiException
	 */
	protected abstract Response getAllEntries() throws ApiException;
}
