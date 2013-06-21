package com.zdonnell.androideveapi.link;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheDatabase {

	// the Activity or Application that is creating an object from this class.
	Context context;

	// a reference to the database used by this application/object
	private SQLiteDatabase db;

	// These constants are specific to the database. They should be
	// changed to suit your needs.
	private final String DB_NAME = "cache_db_new";
	private final int DB_VERSION = 1;

	public final static String TABLE_NAME = "cache_status";

	public final static String TABLE_ID = "request_hash";
	public final static String TABLE_EXPIRE = "cached_until";
	
	private SimpleDateFormat formatter;

	public CacheDatabase(Context context) 
	{
		this.context = context;

		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
		this.db = helper.getWritableDatabase();
		
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	public boolean cacheValid(int requestHash)
	{
		Cursor c = db.query(TABLE_NAME, new String[] { TABLE_EXPIRE }, TABLE_ID + "=?", new String[] { String.valueOf(requestHash) }, null, null, null);
		
		if (c.moveToFirst())
		{
			String dateTime = c.getString(0);
			c.close();

			Date cachedUntil = new Date();

			try { cachedUntil = formatter.parse(dateTime); }
			catch (ParseException e) { e.printStackTrace();	}

			Date now = Calendar.getInstance().getTime();

			if (now.before(cachedUntil)) return true;
			else return false;
		}
		else
		{
			c.close();
			return false;
		}
	}
	
	public boolean cacheExists(int requestHash)
	{
		Cursor c = db.query(TABLE_NAME, new String[] { TABLE_EXPIRE }, TABLE_ID + "=?", new String[] { String.valueOf(requestHash) }, null, null, null);
		
		boolean cacheExists;
		if (c.moveToFirst()) cacheExists = true;
		else cacheExists = false;
		
		c.close();
		
		return cacheExists;
	}
	
	public void updateCache(int requestHash, Date cachedUntil) 
	{	
		if (cachedUntil != null)
		{
			ContentValues values = new ContentValues();
			values.put(TABLE_ID, requestHash);
			values.put(TABLE_EXPIRE, formatter.format(cachedUntil));
			
			try { db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}

	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper 
	{
		public CustomSQLiteOpenHelper(Context context) 
		{
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String newTableQueryString = "create table " + TABLE_NAME + " ("
					+ TABLE_ID + " integer primary key not null,"
					+ TABLE_EXPIRE + " text,"
					+ "UNIQUE (" + TABLE_ID + ") ON CONFLICT REPLACE);";

			db.execSQL(newTableQueryString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{

		}
	}
}
