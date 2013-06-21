package com.zdonnell.androideveapi.link.database;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zdonnell.androideveapi.character.sheet.ApiSkill;

public class SkillsData {

	public final static String TABLE = "skills";
	
	public final static String COL_UNIQUE_ID = "_id";
	public final static String COL_CHAR_ID = "skill_charid";
	public final static String COL_TYPEID = "skill_typeid";
	public final static String COL_SKILLPOINTS = "skill_skillpoints";
	public final static String COL_LEVEL = "skill_level";
	public final static String COL_UNPUBLISHED = "skill_published";
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
	
	public SkillsData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
	}
	
	/**
	 * Obtains set of skills for the provided characterID
	 * 
	 * @param characterID
	 * @return set of {@link ApiSkill} (empty if no skills were found)
	 */
	public Set<ApiSkill> getSkills(int characterID)
	{
		Set<ApiSkill> skillSet = new HashSet<ApiSkill>();
		String[] columnsToReturn = new String[] { COL_TYPEID, COL_SKILLPOINTS, COL_LEVEL, COL_UNPUBLISHED };
		
		Cursor c = db.query(TABLE, columnsToReturn, COL_CHAR_ID + " = ?", new String[] { String.valueOf(characterID) }, null, null, null);
		while (c.moveToNext())
		{
			ApiSkill skill = new ApiSkill();
			skill.setLevel(c.getInt(c.getColumnIndex(COL_LEVEL)));
			skill.setSkillpoints(c.getInt(c.getColumnIndex(COL_SKILLPOINTS)));
			skill.setTypeID(c.getInt(c.getColumnIndex(COL_TYPEID)));
			skill.setUnpublished(c.getInt(c.getColumnIndex(COL_UNPUBLISHED)) == 1 ? true : false);
			
			skillSet.add(skill);
		}
		c.close();
		
		return skillSet;
	}
	
	/**
	 * Inserts the provided skills and links with the provided characterID.
	 * If the database already contains an entry for a characterID + skill typeID it will be replaced
	 * 
	 * @param characterID the character ID to link the skills to
	 * @param skillSet set of {@link ApiSkill} to insert
	 */
	public void storeSkills(int characterID, Set<ApiSkill> skillSet)
	{
		db.beginTransaction();
		
		ContentValues insertValues = new ContentValues();
		
		for (ApiSkill skill : skillSet)
		{
			insertValues.put(COL_CHAR_ID, characterID);
			insertValues.put(COL_TYPEID, skill.getTypeID());
			insertValues.put(COL_SKILLPOINTS, skill.getSkillpoints());
			insertValues.put(COL_LEVEL, skill.getLevel());
			insertValues.put(COL_UNPUBLISHED, skill.isUnpublished() ? 1 : 0);
			
			db.insert(TABLE, null, insertValues);
			insertValues.clear();
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
}
