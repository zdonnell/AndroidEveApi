package com.zdonnell.androideveapi.link.character;

import android.content.Context;
import android.os.AsyncTask;

import com.zdonnell.androideveapi.character.sheet.CharacterSheetResponse;
import com.zdonnell.androideveapi.character.skill.queue.SkillQueueResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.eve.character.CharacterInfoResponse;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.shared.assetlist.AssetListResponse;
import com.zdonnell.androideveapi.shared.wallet.journal.WalletJournalResponse;
import com.zdonnell.androideveapi.shared.wallet.transactions.WalletTransactionsResponse;

public class ApiCharacter
{
	private Context context;
	private ApiAuth<?> apiAuth;
	
	public ApiCharacter(Context context, ApiAuth<?> apiAuth)
	{
		this.context = context;
		this.apiAuth = apiAuth;
	}
	
	public ApiAuth<?> getApiAuth()
	{
		return apiAuth;
	}
	
	public void getCharacterSheet(ApiExceptionCallback<CharacterSheetResponse> callback)
	{
		new CharacterSheetTask(callback, apiAuth, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
	
	public void getCharacterInfo(ApiExceptionCallback<CharacterInfoResponse> callback)
	{
		new CharacterInfoTask(callback, apiAuth, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
	
	public void getSkillQueue(ApiExceptionCallback<SkillQueueResponse> callback)
	{
		new SkillQueueTask(callback, apiAuth, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
	}
	
	public void getWalletJournal(ApiExceptionCallback<WalletJournalResponse> callback)
	{
		new WalletJournalTask(callback, apiAuth, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	public void getWalletTransactions(ApiExceptionCallback<WalletTransactionsResponse> callback)
	{
		new WalletTransactionsTask(callback, apiAuth, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	public void getAssets(ApiExceptionCallback<AssetListResponse> callback)
	{
		new AssetsTask(callback, apiAuth, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
}
