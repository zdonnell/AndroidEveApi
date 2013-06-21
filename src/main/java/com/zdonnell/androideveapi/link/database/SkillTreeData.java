package com.zdonnell.androideveapi.link.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.zdonnell.androideveapi.eve.skilltree.ApiRequirement;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkill;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkillGroup;
import com.zdonnell.androideveapi.eve.skilltree.CharacterAttribute;

public class SkillTreeData {
	
	public final static String TABLE = "skill_tree";
	
	public final static String COL_SKILL_TYPE_ID = "_id";
	public final static String COL_SKILL_NAME = "skill_name";
	public final static String COL_SKILL_DESCRIPTION = "skill_description";
	public final static String COL_SKILL_RANK = "skill_rank";
	public final static String COL_SKILL_PRIM_ATTR = "skill_primattr";
	public final static String COL_SKILL_SEC_ATTR = "skill_secattr";
	public final static String COL_SKILL_PUBLISHED = "skill_published";
	public final static String COL_SKILL_PREREQUESITES = "skill_prereqs";
	public final static String COL_SKILL_GROUP_ID = "skill_groupid";
	public final static String COL_SKILL_GROUP_NAME = "skill_groupname";
	
	private int typeIDIndex;
	private int nameIndex;
	private int descIndex;
	private int rankIndex;
	private int primAttrIndex;
	private int secAttrIndex;
	private int pubIndex;
	private int preReqIndex;
	private int groupIDIndex;
	private int groupNameIndex;
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
	
	public SkillTreeData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
	}
	
	/**
	 * Updates the list of skills that compose the Skill Tree (this will overwrite prior data)
	 * 
	 * @param skillTree
	 */
	public void setSkillTree(Set<ApiSkillGroup> skillTree)
	{
		db.beginTransaction();
		
		ContentValues insertValues = new ContentValues();
		
		for (ApiSkillGroup group : skillTree)
		{
			for (ApiSkill skill : group.getSkills())
			{
				insertValues.put(COL_SKILL_TYPE_ID, skill.getTypeID());
				insertValues.put(COL_SKILL_NAME, skill.getTypeName());
				insertValues.put(COL_SKILL_DESCRIPTION, skill.getDescription());
				insertValues.put(COL_SKILL_RANK, skill.getRank());
				insertValues.put(COL_SKILL_PRIM_ATTR, characterAttributeToInt(skill.getPrimaryAttribute()));
				insertValues.put(COL_SKILL_SEC_ATTR, characterAttributeToInt(skill.getSecondaryAttribute()));
				insertValues.put(COL_SKILL_PUBLISHED, skill.isPublished() ? 1 : 0);
				insertValues.put(COL_SKILL_PREREQUESITES, generatePrerequiresiteString(skill.getRequiredSkills()));
				insertValues.put(COL_SKILL_GROUP_ID, group.getGroupID());
				insertValues.put(COL_SKILL_GROUP_NAME, group.getGroupName());
				
				db.insertWithOnConflict(TABLE, null, insertValues, SQLiteDatabase.CONFLICT_REPLACE);
				insertValues.clear();
			}
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	/**
	 * Given a collection of required skills this function will generate a string for easy storage in a database
	 * 
	 * @param requiredSkills
	 * @return a string in the format of typeID:level,typeID:level for all requiredSkills
	 */
	private String generatePrerequiresiteString(Collection<ApiRequirement> requiredSkills)
	{
		String preReqString = "";
		
		int count = 1;
		for (ApiRequirement req : requiredSkills) 
		{
			preReqString += req.getTypeID() + ":" + req.getSkillLevel();
			if (count < requiredSkills.size()) preReqString += ",";
			
			count++;
		}
		
		return preReqString;
	}
	
	/**
	 * Obtains a fully assembled Skill Tree
	 * 
	 * @return
	 */
	public Set<ApiSkillGroup> getSkillTree()
	{
		SparseArray<ApiSkillGroup> temporaryGroups = new SparseArray<ApiSkillGroup>();
	
		Cursor c = db.query(TABLE, null, null, null, null, null, null);
		
		typeIDIndex = c.getColumnIndex(COL_SKILL_TYPE_ID);
		nameIndex = c.getColumnIndex(COL_SKILL_NAME);
		descIndex = c.getColumnIndex(COL_SKILL_DESCRIPTION);
		rankIndex = c.getColumnIndex(COL_SKILL_RANK);
		pubIndex = c.getColumnIndex(COL_SKILL_PUBLISHED); 
		groupIDIndex = c.getColumnIndex(COL_SKILL_GROUP_ID);
		primAttrIndex = c.getColumnIndex(COL_SKILL_PRIM_ATTR);
		secAttrIndex = c.getColumnIndex(COL_SKILL_SEC_ATTR);
		groupNameIndex = c.getColumnIndex(COL_SKILL_GROUP_NAME);
		preReqIndex = c.getColumnIndex(COL_SKILL_PREREQUESITES);
		
		while (c.moveToNext())
		{
			ApiSkill skill = composeSkill(c);
			
			if (temporaryGroups.get(skill.getGroupID()) == null)
			{
				ApiSkillGroup group = new ApiSkillGroup();
				group.setGroupID(skill.getGroupID());
				group.setGroupName(c.getString(groupNameIndex));
				
				temporaryGroups.put(skill.getGroupID(), group);
			}
			
			temporaryGroups.get(skill.getGroupID()).add(skill);
		}
		
		Set<ApiSkillGroup> skillGroups = new HashSet<ApiSkillGroup>();
		for (int i = 0; i < temporaryGroups.size(); ++i) skillGroups.add(temporaryGroups.valueAt(i));
				
		return skillGroups;
	}
	
	/**
	 * Parses an ApiSkill from the cursor at the cursors current position
	 * 
	 * @param c a cursor positioned at the desired skill to be parsed
	 * @return a fully assembled {@link ApiSkill}
	 */
	private ApiSkill composeSkill(Cursor c)
	{
		ApiSkill skill = new ApiSkill();
		
		int skillTypeID = c.getInt(typeIDIndex);
		String skillName = c.getString(nameIndex);
		String skillDescription = c.getString(descIndex);
		int skillRank = c.getInt(rankIndex);
		boolean isPublished = c.getInt(pubIndex) == 1; 
		int groupID = c.getInt(groupIDIndex);
		int primAttr = c.getInt(primAttrIndex);
		int secAttr = c.getInt(secAttrIndex);
		
		skill.setTypeID(skillTypeID);
		skill.setTypeName(skillName);
		skill.setDescription(skillDescription);
		skill.setRank(skillRank);
		skill.setPublished(isPublished);
		skill.setGroupID(groupID);
		
		skill.setPrimaryAttribute(integerToCharacterAttribute(primAttr));
		skill.setSecondaryAttribute(integerToCharacterAttribute(secAttr));
				
		// The prerequisites are stored in the database in the form of typeID:level,typeID:level ... typeID:level
		String preRequesitesString = c.getString(preReqIndex);
		
		if (preRequesitesString != null && !preRequesitesString.equals(""))
		{
			String[] preReqs = preRequesitesString.split(",");
			for (String preReq : preReqs)
			{
				String[] preReqSplit = preReq.split(":");
				
				ApiRequirement skillPreReq = new ApiRequirement();
				skillPreReq.setTypeID(Integer.valueOf(preReqSplit[0]));
				skillPreReq.setSkillLevel(Integer.valueOf(preReqSplit[1]));
				
				skill.add(skillPreReq);
			}
		}
		
		return skill;
	}
	
	/**
	 * Converts an integer to a {@link CharacterAttribute} 
	 * 
	 * @param i
	 * @return the {@link CharacterAttribute} represented by the provided int
	 */
	private CharacterAttribute integerToCharacterAttribute(int i)
	{
		switch (i)
		{
		case 0:
			return CharacterAttribute.INTELLIGENCE;
		case 1:
			return CharacterAttribute.PERCEPTION;
		case 2:
			return CharacterAttribute.CHARISMA;
		case 3:
			return CharacterAttribute.WILLPOWER;
		case 4:
			return CharacterAttribute.MEMORY;
		default:
			return null;
		}
	}
	
	/**
	 * 
	 * 
	 * @param attribute
	 * @return an int representing that attribute
	 */
	private int characterAttributeToInt(CharacterAttribute attribute)
	{
		if (attribute == CharacterAttribute.INTELLIGENCE) return 0;
		if (attribute == CharacterAttribute.PERCEPTION) return 1;
		if (attribute == CharacterAttribute.CHARISMA) return 2;
		if (attribute == CharacterAttribute.WILLPOWER) return 3;
		if (attribute == CharacterAttribute.MEMORY) return 4;
		
		return 0;
	}
}
