package com.zdonnell.androideveapi.link.character;

import java.util.Arrays;
import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.character.wallet.transactions.WalletTransactionsParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.IApiTask;
import com.zdonnell.androideveapi.link.database.WalletTransactionData;
import com.zdonnell.androideveapi.shared.wallet.transactions.ApiWalletTransaction;
import com.zdonnell.androideveapi.shared.wallet.transactions.WalletTransactionsResponse;

/**
 * AsyncTask to retrieve wallet journal information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class WalletTransactionsTask extends AsyncTask<Void, Void, WalletTransactionsResponse> implements IApiTask<WalletTransactionsResponse>
{		
	private ApiExceptionCallback<WalletTransactionsResponse> callback;
	private ApiAuth<?> apiAuth;
	private Context context;
	
	private boolean apiExceptionOccured = false;
	private ApiException exception;
	
	private final int batchSize = 2560; // Number of rows to grab each iteration. (max ccp allows is 2560).
		
	private WalletTransactionsResponse cachedData;
	private WalletTransactionData transactionsDatabase;
		
	/**
	 * Constructor
	 * 
	 * @param callback
	 * @param apiAuth
	 * @param context
	 */
	public WalletTransactionsTask(ApiExceptionCallback<WalletTransactionsResponse> callback, ApiAuth<?> apiAuth, Context context)
	{
		this.callback = callback;
		this.apiAuth = apiAuth;
		this.context = context;
		
		callback.updateState(ApiExceptionCallback.STATE_CACHED_RESPONSE_ACQUIRED_INVALID);
		transactionsDatabase = new WalletTransactionData(context);
	}
	
	@Override
	protected WalletTransactionsResponse doInBackground(Void... params)
	{
		long newestRefID = transactionsDatabase.mostRecentID(apiAuth.getCharacterID().intValue());
		
		WalletTransactionsParser parser = WalletTransactionsParser.getInstance();		
		WalletTransactionsResponse response = null;
		
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
		        	response = parser.getTransactionsResponse(apiAuth, lastBatchFinalRefID, batchSize);
		        	if (response.getAll().size() == 0) ranOutOfEntries = true;
		        	
		        	for (ApiWalletTransaction entry : response.getAll())
		        	{
		        		if (responseDoesNotContainID(cachedData, entry.getTransactionID())) cachedData.add(entry);
		        	}
		        	
		        	if (!ranOutOfEntries) transactionsDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), cachedData.getAll());
	        	}
	        	while (responseDoesNotContainID(response, newestRefID) && !ranOutOfEntries);
	        }
			catch (ApiException e) 
			{
				apiExceptionOccured = true;
				exception = e;
			}
		}
		// There are no existing journal entries, start from scratch and request all available journal entries
		else
		{		
			cachedData = new WalletTransactionsResponse();
			
	        try 
	        { 	        	
	        	int lastBatchActualSize = 0;
	        	long lastBatchFinalRefID = 0;
	        	
	        	do
	        	{	    	        		
	        		response = parser.getTransactionsResponse(apiAuth, lastBatchFinalRefID, batchSize);
		        	
		        	lastBatchActualSize = response.getAll().size();
		        	lastBatchFinalRefID = getLastID(response.getAll());
		        	
		        	for (ApiWalletTransaction entry : response.getAll()) cachedData.add(entry);
		        	if (lastBatchActualSize > 0) transactionsDatabase.insertJournalEntries(apiAuth.getCharacterID().intValue(), cachedData.getAll());
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
	protected void onPostExecute(WalletTransactionsResponse response) 
	{	
		if (apiExceptionOccured)
		{
			callback.updateState(ApiExceptionCallback.STATE_SERVER_RESPONSE_FAILED);
			callback.onError(cachedData, exception);
		}
		else
		{
			callback.updateState(ApiExceptionCallback.STATE_SERVER_RESPONSE_ACQUIRED);
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

	public WalletTransactionsResponse buildResponseFromDatabase() 
	{
		WalletTransactionsResponse response = new WalletTransactionsResponse();
		
		Set<ApiWalletTransaction> entries = new WalletTransactionData(context).getJournalEntries(apiAuth.getCharacterID().intValue());
		for (ApiWalletTransaction entry : entries) response.add(entry);
		
		return response;
	}
	
	/**
	 * Sorts the transactions in the provide response by transactionID
	 * and returns the oldest ID
	 * 
	 * @return the LastRefID (i.e. oldest chronologically)
	 */
	private long getLastID(Set<ApiWalletTransaction> transactions)
	{
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
	private boolean responseDoesNotContainID(WalletTransactionsResponse response, long transactionID)
	{
		for (ApiWalletTransaction entry : response.getAll())
		{
			if (entry.getTransactionID() == transactionID) return false;
		}
		
		return true;
	}
}

