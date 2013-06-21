package com.zdonnell.androideveapi.link.character;

import java.util.Set;

import android.content.Context;

import com.zdonnell.androideveapi.character.skill.queue.ApiSkillQueueItem;
import com.zdonnell.androideveapi.character.skill.queue.SkillQueueParser;
import com.zdonnell.androideveapi.character.skill.queue.SkillQueueResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;
import com.zdonnell.androideveapi.link.database.SkillQueueData;

/**
 * AsyncTask to retrieve character sheet information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class SkillQueueTask extends APITask<Void, Void, SkillQueueResponse>
{
	final private ApiAuth<?> apiAuth;
	
	public SkillQueueTask(APIExceptionCallback<SkillQueueResponse> callback, final ApiAuth<?> apiAuth, final Context context)
	{
		super(callback, context, true, new EveApiInteraction<SkillQueueResponse>(){

			@Override
			public SkillQueueResponse perform() throws ApiException
			{
				SkillQueueParser parser = SkillQueueParser.getInstance();		
				SkillQueueResponse response = parser.getResponse(apiAuth);
	        	
	        	new SkillQueueData(context).setQueueSkills(apiAuth.getCharacterID().intValue(), response.getAll());
		        	
		        return response;
			}
			
		});
		
		this.apiAuth = apiAuth;
	}
	
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.SKILL_QUEUE.getPage()).hashCode();
	}

	public SkillQueueResponse buildResponseFromDatabase() 
	{
		SkillQueueResponse response = new SkillQueueResponse();
		
		SkillQueueData skillQueueData = new SkillQueueData(context);
		Set<ApiSkillQueueItem> queue = skillQueueData.getQueue(apiAuth.getCharacterID().intValue());
		for (ApiSkillQueueItem queuedSkill : queue) response.add(queuedSkill);
		
		return response;
	}
}

