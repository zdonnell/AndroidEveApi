package com.zdonnell.androideveapi.link.character;

import java.util.Set;

import android.content.Context;

import com.zdonnell.androideveapi.character.sheet.ApiAttributeEnhancer;
import com.zdonnell.androideveapi.character.sheet.ApiSkill;
import com.zdonnell.androideveapi.character.sheet.CharacterSheetParser;
import com.zdonnell.androideveapi.character.sheet.CharacterSheetResponse;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.APIExceptionCallback;
import com.zdonnell.androideveapi.link.APITask;
import com.zdonnell.androideveapi.link.database.AttributesData;
import com.zdonnell.androideveapi.link.database.CharacterSheetData;
import com.zdonnell.androideveapi.link.database.SkillsData;

/**
 * AsyncTask to retrieve character sheet information and provide it to the specified callback
 * 
 * @author Zach
 *
 */
public class CharacterSheetTask extends APITask<Void, Void, CharacterSheetResponse>
{	
	final private ApiAuth<?> apiAuth;
	
	public CharacterSheetTask(APIExceptionCallback<CharacterSheetResponse> callback, final ApiAuth<?> apiAuth, final Context context)
	{
		super(callback, context, true, new EveApiInteraction<CharacterSheetResponse>(){

			@Override
			public CharacterSheetResponse perform() throws ApiException
			{
				CharacterSheetParser parser = CharacterSheetParser.getInstance();		
				CharacterSheetResponse response = parser.getResponse(apiAuth);;
		        	
	        	new CharacterSheetData(context).setCharacterSheet(response);
	        	new SkillsData(context).storeSkills((int) response.getCharacterID(), response.getSkills());
	        	new AttributesData(context).setImplants((int) response.getCharacterID(), response.getAttributeEnhancers());
		        	
		        return response;
			}
			
		});
		
		this.apiAuth = apiAuth;
	}
	
	public int requestTypeHash() 
	{
		return ApiPath.CHARACTER.getPath().concat(ApiPage.CHARACTER_SHEET.getPage()).hashCode();
	}

	public CharacterSheetResponse buildResponseFromDatabase() 
	{
		CharacterSheetResponse response = new CharacterSheetData(context).getCharacterSheet(apiAuth.getCharacterID().intValue());
		
		// Get attributes
		Set<ApiAttributeEnhancer> implants = new AttributesData(context).getImplants(apiAuth.getCharacterID().intValue());
		for (ApiAttributeEnhancer enhancer : implants) response.addAttributeEnhancer(enhancer);
		
		// Get skills
		SkillsData skillsData = new SkillsData(context);
		Set<ApiSkill> skills = skillsData.getSkills(apiAuth.getCharacterID().intValue());
		for (ApiSkill s : skills) response.addSkill(s);
		
		return response;
	}
}

