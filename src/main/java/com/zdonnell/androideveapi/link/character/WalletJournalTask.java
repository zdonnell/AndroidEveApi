package com.zdonnell.androideveapi.link.character;

import java.util.Arrays;
import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.character.wallet.journal.WalletJournalParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.WalletJournalData;
import com.zdonnell.androideveapi.shared.wallet.journal.ApiJournalEntry;
import com.zdonnell.androideveapi.shared.wallet.journal.WalletJournalResponse;

/**
 * AsyncTask to retrieve wallet journal information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class WalletJournalTask extends AsyncTask<Void, Void, WalletJournalResponse> implements IApiTask<WalletJournalResponse>
{		
	private APIExceptionCallback<WalletJournalResponse> callback;
	private ApiAuth<?> apiAuth;
	private Context context;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	
	private final int batchSize = 2560; // Number of rows to grab each iteration. (max ccp allows is 2560).
		
	private WalletJournalResponse cachedData;
	private WalletJournalData journalDatabase;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public WalletJournalTask(APIExceptionCallback<WalletJournalResponse> callback, ApiAuth<?> apiAuth, Context context)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		this.context = context;
		
		callback.updateState(APIExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
		journalDatabase = new WalletJournalData(context);
	}
	
	@Override
	protected WalletJournalResponse doInBackground(Void... params)
	{
		long newestRefID = journalDatabase.mostRecentRefID(apiAuth.getCharacterID().intValue());
		
		WalletJournalParser parser = WalletJournalParser.getInstance();		
		WalletJournalResponse response = null;
		
		// There are at least some entries for this character, update the UI
		// with the old stuff while we wait for the new to load from the server
		if (newestRefID != 0)
		{
			cachedData = buildResponseFromDatabase();
			publishProgress();
			
        	long lastBatchFinalRefID = 0;
			boolean ranOutOfEntries = false;
        	
			try 
	        { 	     
	        	do
	        	{	    
		        	response = parser.getWalletJournalResponse(apiAuth, lastBatchFinalRefID, batchSize);
		        	if (response.getAll().size() == 0) ranOutOfEntries = true;
		        	
		        	for (ApiJournalEntry entry : response.getAll())
		        	{
		        		if (responseDoesNotContainRefID(cachedData, entry.getRefID())) cachedData.add(entry);
		        	}
		        	
		        	if (!ranOutOfEntries) journalDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), cachedData.getAll());
	        	}
	        	while (responseDoesNotContainRefID(response, newestRefID) && !ranOutOfEntries);
	        }
			catch (ApiException e) 
			{
				apiExceptionOccured = true;
				exception = e;
			}
		}
		
		else // There are no existing journal entries, start from scratch and request all available journal entries
		{		
			cachedData = new WalletJournalResponse();
			
	        try 
	        { 	        	
	        	int lastBatchActualSize = 0;
	        	long lastBatchFinalRefID = 0;
	        	
	        	do
	        	{	    	        		
	        		response = parser.getWalletJournalResponse(apiAuth, lastBatchFinalRefID, batchSize);
		        	
		        	lastBatchActualSize = response.getAll().size();
		        	if (lastBatchActualSize > 0) lastBatchFinalRefID = getLastRefID(response.getAll());
		        	
		        	for (ApiJournalEntry entry : response.getAll()) cachedData.add(entry);
		        	if (lastBatchActualSize > 0) journalDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), cachedData.getAll());
	        	}
	        	while (lastBatchActualSize == batchSize); // Loop through older and older entries until there are less than requested 
	        }
			catch (ApiException e) 
			{
				apiExceptionOccured = true;
				exception = e;
			}
		}
		
        return cachedData;
	}
	
	@Override
	protected void onPostExecute(WalletJournalResponse response) 
	{	
		if (apiExceptionOccured)
		{
			callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
			callback.onError(cachedData, exception);
		}
		else
		{
			callback.updateState(APIExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
			callback.onUpdate(cachedData);
		}
    }

	@Override
	protected void onProgressUpdate(Void... progress)
	{		
		callback.onUpdate(cachedData);
	}
	
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.WALLET_JOURNAL.getPage()).hashCode();
	}

	public WalletJournalResponse buildResponseFromDatabase() 
	{
		WalletJournalResponse response = new WalletJournalResponse();
		
		Set<ApiJournalEntry> entries = new WalletJournalData(context).getJournalEntries(apiAuth.getCharacterID().intValue());
		for (ApiJournalEntry entry : entries) response.add(entry);
		
		return response;
	}
	
	/**
	 * Sorts the journal entries in the provide response by refID
	 * and returns the oldest refID
	 * 
	 * @return the LastRefID (i.e. oldest chronologically)
	 */
	private long getLastRefID(Set<ApiJournalEntry> entries)
	{
		ApiJournalEntry[] entryArray = new ApiJournalEntry[entries.size()];
		entries.toArray(entryArray);
		
		Arrays.sort(entryArray, new WalletSort.Journal.RefID());
		
		return entryArray[entryArray.length - 1].getRefID();
	}
	
	/**
	 * Checks to see if the provided WalletJournalResponse contains an entry with a refID
	 * matching that of the provided refID.
	 * 
	 * @param response
	 * @param refID
	 * @return True if the response does not contain the specified refID.
	 */
	private boolean responseDoesNotContainRefID(WalletJournalResponse response, long refID)
	{
		for (ApiJournalEntry entry : response.getAll())
		{
			if (entry.getRefID() == refID) return false;
		}
		
		return true;
	}
}

