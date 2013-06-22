package com.zdonnell.androideveapi.link.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zdonnell.androideveapi.character.sheet.CharacterSheetResponse;
import com.zdonnell.androideveapi.shared.character.EveAncestry;
import com.zdonnell.androideveapi.shared.character.EveBloodline;

public class CharacterSheetData {
	
	public final static String TABLE = "character_sheet";
	
	public final static String COL_CHARACTER_ID = "_id";
	public final static String COL_NAME = "character_name";
	public final static String COL_RACE = "character_race";
	public final static String COL_DOB = "character_dateofbirth";
	public final static String COL_BLOODLINE = "character_bloodline";
	public final static String COL_ANCESTRY = "character_ancestry";
	public final static String COL_GENDER = "character_gender";
	public final static String COL_CORP_NAME = "character_corpname";
	public final static String COL_CORP_ID = "character_corpid";
	public final static String COL_ALLIANCE_ID = "character_allianceid";
	public final static String COL_ALLIANCE = "character_alliance";
	public final static String COL_CLONE_NAME = "character_clonename";
	public final static String COL_CLONE_SP = "character_clonesp";	
	public final static String COL_BALANCE = "character_balance";
	
	public final static String COL_INTELLIGENCE = "character_intel";
	public final static String COL_MEMORY = "character_memory";
	public final static String COL_CHARISMA = "character_charisma";
	public final static String COL_PERCEPTION = "character_perception";
	public final static String COL_WILLPOWER = "character_willpower";
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
	
	private SimpleDateFormat formatter;
	
	public CharacterSheetData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	}
	
	/**
	 * 
	 * @param characterInfo
	 */
	public void setCharacterSheet(CharacterSheetResponse characterSheet)
	{		
		ContentValues insertValues = new ContentValues();
		
		insertValues.put(COL_CHARACTER_ID, characterSheet.getCharacterID());
		insertValues.put(COL_NAME, characterSheet.getName());
		//insertValues.put(COL_RACE, characterSheet.getRace().name()); TODO figure out why eve race breaks
		insertValues.put(COL_DOB, formatter.format(characterSheet.getDateOfBirth()));
		insertValues.put(COL_BLOODLINE, characterSheet.getBloodLine().name());
		insertValues.put(COL_ANCESTRY, characterSheet.getAncestry().name());
		insertValues.put(COL_GENDER, characterSheet.getGender());
		insertValues.put(COL_CORP_NAME, characterSheet.getCorporationName());
		insertValues.put(COL_CORP_ID, characterSheet.getCorporationID());
		insertValues.put(COL_ALLIANCE_ID, characterSheet.getAllianceID());
		insertValues.put(COL_ALLIANCE, characterSheet.getAllianceName());
		insertValues.put(COL_CLONE_NAME, characterSheet.getCloneName());
		insertValues.put(COL_CLONE_SP, characterSheet.getCloneSkillPoints());
		insertValues.put(COL_BALANCE, characterSheet.getBalance());
		
		insertValues.put(COL_INTELLIGENCE, characterSheet.getIntelligence());
		insertValues.put(COL_MEMORY, characterSheet.getMemory());
		insertValues.put(COL_CHARISMA, characterSheet.getCharisma());
		insertValues.put(COL_PERCEPTION, characterSheet.getPerception());
		insertValues.put(COL_WILLPOWER, characterSheet.getWillpower());
				
		db.insertWithOnConflict(TABLE, null, insertValues, SQLiteDatabase.CONFLICT_REPLACE);
	}
	
	/**
	 *  
	 * @param characterID
	 * @return
	 */
	public CharacterSheetResponse getCharacterSheet(int characterID)
	{
		CharacterSheetResponse characterSheet = new CharacterSheetResponse();
	
		Cursor c = db.query(TABLE, null, COL_CHARACTER_ID + " = ?", new String[] { String.valueOf(characterID) }, null, null, null);
		
		if (c.moveToFirst())
		{
			characterSheet.setCharacterID(c.getLong(c.getColumnIndex(COL_CHARACTER_ID)));
			characterSheet.setName(c.getString(c.getColumnIndex(COL_NAME)));
			//characterSheet.setRace(EveRace.valueOf(c.getString(c.getColumnIndex(COL_RACE)))); TODO Fix eve race.
			
			try { characterSheet.setDateOfBirth(formatter.parse(c.getString(c.getColumnIndex(COL_DOB)))); } 
			catch (ParseException e) { e.printStackTrace(); }
			
			characterSheet.setBloodLine(EveBloodline.valueOf(c.getString(c.getColumnIndex(COL_BLOODLINE))));
			characterSheet.setAncestry(EveAncestry.valueOf(c.getString(c.getColumnIndex(COL_ANCESTRY))));
			characterSheet.setGender(c.getString(c.getColumnIndex(COL_GENDER)));
			characterSheet.setCorporationName(c.getString(c.getColumnIndex(COL_CORP_NAME)));
			characterSheet.setCorporationID(c.getLong(c.getColumnIndex(COL_CORP_ID)));
			characterSheet.setAllianceID(c.getLong(c.getColumnIndex(COL_ALLIANCE_ID)));
			characterSheet.setAllianceName(c.getString(c.getColumnIndex(COL_ALLIANCE)));
			characterSheet.setCloneName(c.getString(c.getColumnIndex(COL_CLONE_NAME)));
			characterSheet.setCloneSkillPoints(c.getLong(c.getColumnIndex(COL_CLONE_SP)));
			characterSheet.setBalance(c.getDouble(c.getColumnIndex(COL_BALANCE)));
			
			characterSheet.setIntelligence(c.getInt(c.getColumnIndex(COL_INTELLIGENCE)));
			characterSheet.setMemory(c.getInt(c.getColumnIndex(COL_MEMORY)));
			characterSheet.setCharisma(c.getInt(c.getColumnIndex(COL_CHARISMA)));
			characterSheet.setPerception(c.getInt(c.getColumnIndex(COL_PERCEPTION)));
			characterSheet.setWillpower(c.getInt(c.getColumnIndex(COL_WILLPOWER)));
		}
		
		c.close();
				
		return characterSheet;
	}
}
