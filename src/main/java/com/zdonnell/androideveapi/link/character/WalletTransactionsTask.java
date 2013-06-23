package com.zdonnell.androideveapi.link.character;

import java.util.Arrays;
import java.util.Set;

import android.content.Context;

import com.zdonnell.androideveapi.character.wallet.transactions.WalletTransactionsParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.ApiSerialTask;
import com.zdonnell.androideveapi.link.database.WalletTransactionData;
import com.zdonnell.androideveapi.shared.wallet.transactions.ApiWalletTransaction;
import com.zdonnell.androideveapi.shared.wallet.transactions.WalletTransactionsResponse;

/**
 * AsyncTask to retrieve wallet journal information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class WalletTransactionsTask extends ApiSerialTask<Void, Void, WalletTransactionsResponse> {				
	private WalletTransactionData transactionsDatabase;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public WalletTransactionsTask(ApiExceptionCallback<WalletTransactionsResponse> callback, ApiAuth<?> apiAuth, Context context) {
		super(callback, context, apiAuth, 2560);		
		transactionsDatabase = new WalletTransactionData(context);
	}
	
	@Override
	protected void checkForNewerEntries(WalletTransactionsResponse finalResponse, long newestRefID) throws ApiException {
		long lastBatchFinalRefID = 0;
		boolean ranOutOfEntries = false;

		WalletTransactionsResponse response;
    	WalletTransactionsParser parser = WalletTransactionsParser.getInstance();
		
    	do {	    
        	response = parser.getTransactionsResponse(apiAuth, lastBatchFinalRefID, batchSize);
        	if (response.getAll().size() == 0) ranOutOfEntries = true;
        	
        	for (ApiWalletTransaction entry : response.getAll()) {
        		if (responseDoesNotContainID(finalResponse, entry.getTransactionID())) finalResponse.add(entry);
        	}
        	
        	if (!ranOutOfEntries) transactionsDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), finalResponse.getAll());
    	} while (responseDoesNotContainID(response, newestRefID) && !ranOutOfEntries);
	}

	@Override
	protected WalletTransactionsResponse getAllEntries() throws ApiException {
		int lastBatchActualSize = 0;
    	long lastBatchFinalRefID = 0;
    	
    	WalletTransactionsResponse finalResponse = new WalletTransactionsResponse(), response;
    	WalletTransactionsParser parser = WalletTransactionsParser.getInstance();
    	
    	do {	    	        		
    		response = parser.getTransactionsResponse(apiAuth, lastBatchFinalRefID, batchSize);
        	
        	lastBatchActualSize = response.getAll().size();
        	lastBatchFinalRefID = getLastID(response.getAll());
        	
        	for (ApiWalletTransaction entry : response.getAll()) finalResponse.add(entry);
        	if (lastBatchActualSize > 0) transactionsDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), finalResponse.getAll());
    	} while (lastBatchActualSize == batchSize); // Loop through older and older entries until there are less than requested 
		
    	return finalResponse;
	}
	
	@Override
	public WalletTransactionsResponse buildResponseFromDatabase() {
		WalletTransactionsResponse response = new WalletTransactionsResponse();
		
		Set<ApiWalletTransaction> entries = new WalletTransactionData(context).getJournalEntries(apiAuth.getCharacterID().intValue());
		for (ApiWalletTransaction entry : entries) response.add(entry);
		
		return response;
	}
	
	@Override
	protected long getNewestRefID() {
		return transactionsDatabase.mostRecentID(apiAuth.getCharacterID().intValue());
	}
	
	/**
	 * Sorts the transactions in the provide response by transactionID
	 * and returns the oldest ID
	 * 
	 * @return the LastRefID (i.e. oldest chronologically)
	 */
	private long getLastID(Set<ApiWalletTransaction> transactions) {
		ApiWalletTransaction[] transactionsArray = new ApiWalletTransaction[transactions.size()];
		transactions.toArray(transactionsArray);
		
		Arrays.sort(transactionsArray, new WalletSort.Transactions.ID());
		
		return transactionsArray[transactionsArray.length - 1].getTransactionID();
	}
	
	/**
	 * Checks to see if the provided WalletTransactionsResponse contains an entry with a transaction ID
	 * matching that of the provided ID.
	 * 
	 * @param response
	 * @param refID
	 * @return True if the response does not contain the specified refID.
	 */
	private boolean responseDoesNotContainID(WalletTransactionsResponse response, long transactionID) {
		for (ApiWalletTransaction entry : response.getAll()) {
			if (entry.getTransactionID() == transactionID) return false;
		}
		return true;
	}
}

