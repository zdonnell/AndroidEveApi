package com.zdonnell.androideveapi.link.database;

import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zdonnell.androideveapi.eve.reftypes.ApiRefType;

public class RefTypesData {
	
	public final static String TABLE = "ref_types";
	
	public final static String COL_REFTYPE_ID = "reftype_id";
	public final static String COL_REFTYPE_NAME = "reftype_string";
	
	private int typeIDIndex;
	private int nameIndex;
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
	
	public RefTypesData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
	}
	
	/** 
	 * @param refTypeList
	 */
	public void setRefTypes(Set<ApiRefType> refTypeList)
	{
		db.beginTransaction();
		
		ContentValues insertValues = new ContentValues();
		
		for (ApiRefType refType : refTypeList)
		{
			insertValues.put(COL_REFTYPE_ID, refType.getRefTypeID());
			insertValues.put(COL_REFTYPE_NAME, refType.getRefTypeName());
			
			db.insertWithOnConflict(TABLE, null, insertValues, SQLiteDatabase.CONFLICT_REPLACE);
			insertValues.clear();
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	/** 
	 * @return
	 */
	public Set<ApiRefType> getRefTypes()
	{
		Set<ApiRefType> refTypes = new HashSet<ApiRefType>();
	
		Cursor c = db.query(TABLE, null, null, null, null, null, null);
		
		typeIDIndex = c.getColumnIndex(COL_REFTYPE_ID);
		nameIndex = c.getColumnIndex(COL_REFTYPE_NAME);
		
		while (c.moveToNext())
		{
			ApiRefType refType = new ApiRefType();
			refType.setRefTypeID(c.getInt(typeIDIndex));
			refType.setRefTypeName(c.getString(nameIndex));
			refTypes.add(refType);
		}
				
		return refTypes;
	}
}
