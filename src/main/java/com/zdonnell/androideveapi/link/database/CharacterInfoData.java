package com.zdonnell.androideveapi.link.database;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zdonnell.androideveapi.eve.character.CharacterInfoResponse;
import com.zdonnell.androideveapi.shared.character.EveBloodline;
import com.zdonnell.androideveapi.shared.character.EveRace;

public class CharacterInfoData {
	
	public final static String TABLE = "character_info";
	
	public final static String COL_CHARACTER_ID = "_id";
	public final static String COL_RACE = "character_race";
	public final static String COL_BLOODLINE = "character_bloodline";
	public final static String COL_WALLET_BALLANCE = "character_wallbalance";
	public final static String COL_SKILLPOINTS = "character_skillpoints";
	public final static String COL_SHIP_NAME = "character_curshipname";
	public final static String COL_SHIP_TYPEID = "character_curshiptypeid";
	public final static String COL_CORP_DATE = "character_curcorpjoindate";
	public final static String COL_ALLIANCE_ID = "character_allianceid";
	public final static String COL_ALLIANCE = "character_alliance";
	public final static String COL_ALLIANCE_DATE = "character_alliancedate";
	public final static String COL_LAST_KNOWN_LOC = "character_lastknownlocation";
	public final static String COL_SEC_STATUS = "character_security";
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
	
	private SimpleDateFormat formatter;
	
	public CharacterInfoData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * @param characterInfo
	 */
	public void setCharacterInfo(CharacterInfoResponse characterInfo)
	{		
		ContentValues insertValues = new ContentValues();
		
		insertValues.put(COL_CHARACTER_ID, characterInfo.getCharacterID());
		insertValues.put(COL_RACE, characterInfo.getRace().name());
		insertValues.put(COL_BLOODLINE, characterInfo.getBloodline().name());
		insertValues.put(COL_WALLET_BALLANCE, characterInfo.getAccountBalance());
		insertValues.put(COL_SKILLPOINTS, characterInfo.getSkillPoints());
		insertValues.put(COL_SHIP_NAME, characterInfo.getShipName());
		insertValues.put(COL_SHIP_TYPEID, characterInfo.getShipTypeID());
		insertValues.put(COL_CORP_DATE, formatter.format(characterInfo.getCorporationDate()));
		insertValues.put(COL_ALLIANCE_ID, characterInfo.getAllianceID());
		insertValues.put(COL_ALLIANCE, characterInfo.getAlliance());
		
		if (characterInfo.getAllianceDate() != null) insertValues.put(COL_ALLIANCE_DATE, formatter.format(characterInfo.getAllianceDate()));
		
		insertValues.put(COL_LAST_KNOWN_LOC, characterInfo.getLastKnownLocation());
		insertValues.put(COL_SEC_STATUS, characterInfo.getSecurityStatus());
				
		db.insertWithOnConflict(TABLE, null, insertValues, SQLiteDatabase.CONFLICT_REPLACE);
	}
	
	/**
	 *  
	 * @param characterID
	 * @return
	 */
	public CharacterInfoResponse getCharacterInfo(int characterID)
	{
		CharacterInfoResponse characterInfo = new CharacterInfoResponse();
	
		Cursor c = db.query(TABLE, null, COL_CHARACTER_ID + " = ?", new String[] { String.valueOf(characterID) }, null, null, null);
		
		if (c.moveToFirst())
		{
			characterInfo.setCharacterID(c.getLong(c.getColumnIndex(COL_CHARACTER_ID)));
			characterInfo.setRace(stringToEveRace(c.getString(c.getColumnIndex(COL_RACE))));
			characterInfo.setBloodline(stringToEveBloodline(c.getString(c.getColumnIndex(COL_BLOODLINE))));
			characterInfo.setAccountBalance(c.getDouble(c.getColumnIndex(COL_WALLET_BALLANCE)));
			characterInfo.setSkillPoints(c.getInt(c.getColumnIndex(COL_SKILLPOINTS)));
			characterInfo.setShipName(c.getString(c.getColumnIndex(COL_SHIP_NAME)));
			characterInfo.setShipTypeID(c.getInt(c.getColumnIndex(COL_SHIP_TYPEID)));
			
			try { characterInfo.setCorporationDate(formatter.parse(c.getString(c.getColumnIndex(COL_CORP_DATE)))); } 
			catch (Exception e) { e.printStackTrace(); }
			
			characterInfo.setAllianceID(c.getLong(c.getColumnIndex(COL_ALLIANCE_ID)));
			characterInfo.setAlliance(c.getString(c.getColumnIndex(COL_ALLIANCE)));
			
			try { characterInfo.setAllianceDate(formatter.parse(c.getString(c.getColumnIndex(COL_ALLIANCE_DATE)))); } 
			catch (Exception e) { e.printStackTrace(); }
			
			characterInfo.setLastKnownLocation(c.getString(c.getColumnIndex(COL_LAST_KNOWN_LOC)));
			characterInfo.setSecurityStatus(c.getDouble(c.getColumnIndex(COL_SEC_STATUS)));
		}
		
		c.close();
				
		return characterInfo;
	}
	
	/**
	 * Converts a string representing an eve race to it's {@link EveRace} representation
	 * 
	 * @param raceString
	 * @return
	 */
	private EveRace stringToEveRace(String raceString)
	{
		for (EveRace race : EveRace.values())
		{
			if (race.name().equals(raceString)) return race;
		}
		
		return null;
	}
	
	/**
	 * Converts a string representing an eve bloodline to it's {@link EveBloodline} representation
	 * 
	 * @param bloodlineString
	 * @return
	 */
	private EveBloodline stringToEveBloodline(String bloodlineString)
	{
		for (EveBloodline bloodline : EveBloodline.values())
		{
			if (bloodline.name().equals(bloodlineString)) return bloodline;
		}
		
		return null;
	}
}
