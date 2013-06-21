package com.zdonnell.androideveapi.link.account;

import com.zdonnell.androideveapi.account.characters.EveCharacter;
import com.zdonnell.androideveapi.core.ApiAuth;

/**
 * This class is an extension of the eveapi {@link EveCharacter} in order to
 * provide functionality that is more in line with how eden was structured before
 * eveapi integration
 * 
 * @author Zach
 * 
 * @see {@link EveCharacter}
 *
 */
public class EdenEveCharacter extends EveCharacter {

	private long queueTimeRemaining;
	
	private ApiAuth<?> apiAuth;
	
	public void setQueueTimeRemaining(long queueTimeRemaining)
	{
		this.queueTimeRemaining = queueTimeRemaining;
	}
	
	public long getQueueTimeRemaining()
	{
		return queueTimeRemaining;
	}
	
	public void setApiAuth(ApiAuth<?> apiAuth)
	{
		this.apiAuth = apiAuth;
	}
	
	public ApiAuth<?> getApiAuth()
	{
		return apiAuth;
	}
}
