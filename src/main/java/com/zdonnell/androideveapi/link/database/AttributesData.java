package com.zdonnell.androideveapi.link.database;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zdonnell.androideveapi.character.sheet.ApiAttributeEnhancer;
import com.zdonnell.androideveapi.character.sheet.CharismaBonus;
import com.zdonnell.androideveapi.character.sheet.IntelligenceBonus;
import com.zdonnell.androideveapi.character.sheet.MemoryBonus;
import com.zdonnell.androideveapi.character.sheet.PerceptionBonus;
import com.zdonnell.androideveapi.character.sheet.WillpowerBonus;

public class AttributesData {

	public final static String TABLE = "attributes";
	
	public final static String COL_UNIQUE_ID = "_id";
	public final static String COL_CHAR_ID = "attr_charid";
	public final static String COL_IMPLANT_NAME = "attr_implant";
	public final static String COL_IMPLANT_BONUS = "attr_implant_bonus";
	public final static String COL_IMPLANT_SLOT = "attr_slot";
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
	
	public AttributesData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
	}
	
	/**
	 * Gets the set of implants for the provided characterID.
	 * 
	 * @param characterID
	 * @return The set of implants.  Or an empty set if the character has no implants.
	 */
	public Set<ApiAttributeEnhancer> getImplants(int characterID)
	{
		Set<ApiAttributeEnhancer> implants = new HashSet<ApiAttributeEnhancer>();
		String[] columnsToReturn = new String[] { COL_IMPLANT_NAME, COL_IMPLANT_BONUS, COL_IMPLANT_SLOT };
		
		Cursor c = db.query(TABLE, columnsToReturn, COL_CHAR_ID + " = ?", new String[] { String.valueOf(characterID) }, null, null, null);
		while (c.moveToNext())
		{
			ApiAttributeEnhancer enhancer;
			
			int slot = c.getInt(c.getColumnIndex(COL_IMPLANT_SLOT));
			Log.d("SLOT", "SLOT: " + slot);
			switch (slot)
			{
			case 1: enhancer = new IntelligenceBonus(); break;
			case 2: enhancer = new MemoryBonus(); break;
			case 3: enhancer = new CharismaBonus(); break;
			case 4: enhancer = new PerceptionBonus(); break;
			case 5: enhancer = new WillpowerBonus(); break;
			default: enhancer = new WillpowerBonus(); break;
			}
			
			enhancer.setAugmentatorName(c.getString(c.getColumnIndex(COL_IMPLANT_NAME)));
			enhancer.setAugmentatorValue(c.getInt(c.getColumnIndex(COL_IMPLANT_BONUS)));
			
			implants.add(enhancer);
		}
		c.close();
		
		return implants;
	}
	
	/**
	 * Updates the implant list for the specified character.
	 * 
	 * @param characterID
	 * @param enhancers
	 */
	public void setImplants(int characterID, Set<ApiAttributeEnhancer> enhancers)
	{
		db.beginTransaction();
		
		ContentValues insertValues = new ContentValues();
		
		for (ApiAttributeEnhancer enhancer : enhancers)
		{
			int slot = 0;
			if (enhancer.getAttribute().equals("intelligence")) slot = 1;
			else if (enhancer.getAttribute().equals("memory")) slot = 2;
			else if (enhancer.getAttribute().equals("charisma")) slot = 3;
			else if (enhancer.getAttribute().equals("perception")) slot = 4;
			else if (enhancer.getAttribute().equals("willpower")) slot = 5;
			
			insertValues.put(COL_CHAR_ID, characterID);
			insertValues.put(COL_IMPLANT_NAME, enhancer.getAugmentatorName());
			insertValues.put(COL_IMPLANT_BONUS, enhancer.getAugmentatorValue());
			insertValues.put(COL_IMPLANT_SLOT, slot);
			
			db.insertWithOnConflict(TABLE, null, insertValues, SQLiteDatabase.CONFLICT_REPLACE);
			insertValues.clear();
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
}
