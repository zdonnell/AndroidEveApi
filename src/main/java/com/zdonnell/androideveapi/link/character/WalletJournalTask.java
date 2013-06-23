package com.zdonnell.androideveapi.link.character;

import java.util.Arrays;
import java.util.Set;

import android.content.Context;

import com.zdonnell.androideveapi.character.wallet.journal.WalletJournalParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.ApiSerialTask;
import com.zdonnell.androideveapi.link.database.WalletJournalData;
import com.zdonnell.androideveapi.shared.wallet.journal.ApiJournalEntry;
import com.zdonnell.androideveapi.shared.wallet.journal.WalletJournalResponse;

/**
 * AsyncTask to retrieve wallet journal information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class WalletJournalTask extends ApiSerialTask<Void, Void, WalletJournalResponse>
{			
	private WalletJournalData journalDatabase;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public WalletJournalTask(ApiExceptionCallback<WalletJournalResponse> callback, ApiAuth<?> apiAuth, Context context) {
		super(callback, context, apiAuth, 2560);		
		journalDatabase = new WalletJournalData(context);
	}
	
	@Override
	protected void checkForNewerEntries(WalletJournalResponse finalResponse, long newestRefID) throws ApiException {
		long lastBatchFinalRefID = 0;
		boolean ranOutOfEntries = false;
		
		WalletJournalResponse response;
    	WalletJournalParser parser = WalletJournalParser.getInstance();
		
    	do {	    
    		response = parser.getWalletJournalResponse(apiAuth, lastBatchFinalRefID, batchSize);
        	if (response.getAll().size() == 0) ranOutOfEntries = true;
        	
        	for (ApiJournalEntry entry : response.getAll()) {
        		if (responseDoesNotContainRefID(finalResponse, entry.getRefID())) finalResponse.add(entry);
        	}
        	
        	if (!ranOutOfEntries) journalDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), finalResponse.getAll());
    	} while (responseDoesNotContainRefID(response, newestRefID) && !ranOutOfEntries);
	}

	@Override
	protected WalletJournalResponse getAllEntries() throws ApiException {
		int lastBatchActualSize = 0;
    	long lastBatchFinalRefID = 0;
    	
    	WalletJournalResponse finalResponse = new WalletJournalResponse(), response;
    	WalletJournalParser parser = WalletJournalParser.getInstance();
    	
    	do {	    	        		
    		response = parser.getWalletJournalResponse(apiAuth, lastBatchFinalRefID, batchSize);
        	
        	lastBatchActualSize = response.getAll().size();
        	if (lastBatchActualSize > 0) lastBatchFinalRefID = getLastRefID(response.getAll());
        	
        	for (ApiJournalEntry entry : response.getAll()) finalResponse.add(entry);
        	if (lastBatchActualSize > 0) journalDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), finalResponse.getAll());
    	} while (lastBatchActualSize == batchSize); // Loop through older and older entries until there are less than requested
    	
    	return finalResponse;
	}
	
	@Override
	public WalletJournalResponse buildResponseFromDatabase() {
		WalletJournalResponse response = new WalletJournalResponse();
		
		Set<ApiJournalEntry> entries = new WalletJournalData(context).getJournalEntries(apiAuth.getCharacterID().intValue());
		for (ApiJournalEntry entry : entries) response.add(entry);
		
		return response;
	}
	
	@Override
	protected long getNewestRefID() {
		return journalDatabase.mostRecentRefID(apiAuth.getCharacterID().intValue());
	}
	
	/**
	 * Checks to see if the provided WalletJournalResponse contains an entry with a refID
	 * matching that of the provided refID.
	 * 
	 * @param response
	 * @param refID
	 * @return True if the response does not contain the specified refID.
	 */
	private boolean responseDoesNotContainRefID(WalletJournalResponse response, long refID) {
		for (ApiJournalEntry entry : response.getAll()) {
			if (entry.getRefID() == refID) return false;
		}
		return true;
	}
	
	/**
	 * Sorts the journal entries in the provide response by refID
	 * and returns the oldest refID
	 * 
	 * @return the LastRefID (i.e. oldest chronologically)
	 */
	private long getLastRefID(Set<ApiJournalEntry> entries) {
		ApiJournalEntry[] entryArray = new ApiJournalEntry[entries.size()];
		entries.toArray(entryArray);
		
		Arrays.sort(entryArray, new WalletSort.Journal.RefID());
		
		return entryArray[entryArray.length - 1].getRefID();
	}
}