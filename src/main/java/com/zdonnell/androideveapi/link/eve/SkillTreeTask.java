package com.zdonnell.androideveapi.link.eve;

import android.content.Context;
import android.util.SparseArray;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkill;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkillGroup;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeParser;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiCachingTask;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.database.SkillTreeData;

/**
 * 
 * @author Zach
 *
 */
public class SkillTreeTask extends ApiCachingTask<Void, Void, SkillTreeResponse> {		
	public SkillTreeTask(ApiExceptionCallback<SkillTreeResponse> callback, final Context context) {						
		super(callback, context, true, null, new EveApiInteraction<SkillTreeResponse>() {
			@Override
			public SkillTreeResponse perform() throws ApiException {
				SkillTreeParser parser = SkillTreeParser.getInstance();
				SkillTreeResponse response = parser.getResponse();
				fixSkillGroups(response);
				
				SkillTreeData skillTreeData = new SkillTreeData(context);
				skillTreeData.setSkillTree(response.getAll());

				return response;
			}
		});
	}
	
	/**
	 * Removes duplicate groups from a {@link SkillTreeResponse}
	 * 
	 * @param response
	 */
	private static void fixSkillGroups(SkillTreeResponse response) {
		SparseArray<ApiSkillGroup> correctedSkillGroups = new SparseArray<ApiSkillGroup>();

		for (ApiSkillGroup group : response.getAll()) {
			// This is the first time we have seen a group of this ID, add it to the corrected list
			if (correctedSkillGroups.get(group.getGroupID()) == null) {
				correctedSkillGroups.put(group.getGroupID(), group);
			}
			// The group exists in the corrected list already, add all it's skills to the existing group in the corrected list
			else {
				for (ApiSkill skill : group.getSkills()) correctedSkillGroups.get(group.getGroupID()).add(skill);
			}
		}

		// Clear the "unfixed" groups, and add back the fixed ones
		response.getAll().clear();
		for (int i = 0; i < correctedSkillGroups.size(); i++) response.add(correctedSkillGroups.valueAt(i));
	}

	@Override
	public int requestTypeHash() {
		return ApiPath.EVE.getPath().concat(ApiPage.SKILL_TREE.getPage()).hashCode();
	}
	
	@Override
	public SkillTreeResponse buildResponseFromDatabase() {
		SkillTreeResponse response = new SkillTreeResponse();
		
		SkillTreeData skillTree = new SkillTreeData(context);
		for (ApiSkillGroup group : skillTree.getSkillTree()) response.add(group);
		
		return response;
	}
}