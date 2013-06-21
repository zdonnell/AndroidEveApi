package com.zdonnell.androideveapi.link.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	public static final String DB_NAME = "api_data_db";
	public static final int DB_VERSION = 1;
	
	public static class OpenHelper extends SQLiteOpenHelper {
		
		public OpenHelper(Context context) 
		{
			super(context, Database.DB_NAME, null, Database.DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			createSkillsTable(db);
			createSkillTreeDataTable(db);
			createSkillQueueTable(db);
			createCharacterInfoTable(db);
			createCharacterSheetTable(db);
			createCharacterAttributesTable(db);
			
			createCharacterWalletJournalTable(db);
			createCharacterWalletTransactionTable(db);
			
			createAssetsTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{

		}
	}

		
	private static void createAssetsTable(SQLiteDatabase db)
	{	
		String newTableQueryString = "create table " + AssetsData.TABLE + " ("
				
				+ AssetsData.COL_ID + " integer primary key not null," 
				+ AssetsData.COL_LOC_ID + " integer," 
				+ AssetsData.COL_CHAR_ID + " integer," 
				+ AssetsData.COL_PARENT_ID + " integer," 
				+ AssetsData.COL_QUANTITY + " integer,"
				+ AssetsData.COL_TYPE_ID + " integer,"
				+ AssetsData.COL_FLAG + " integer," 
				+ AssetsData.COL_SINGLETON + " integer,"
				+ AssetsData.COL_RAW_QUANTITY + " integer,"
				
				+ "UNIQUE (" + AssetsData.COL_ID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createCharacterWalletTransactionTable(SQLiteDatabase db)
	{
		String newTableQueryString = "create table " + WalletTransactionData.TABLE + " ("
				
				+ WalletTransactionData.COL_ID + " integer primary key not null," 
				+ WalletTransactionData.COL_DATE + " text," 
				+ WalletTransactionData.COL_CHAR_ID + " integer," 
				+ WalletTransactionData.COL_QUANTITY + " integer,"
				+ WalletTransactionData.COL_TYPENAME + " text,"
				+ WalletTransactionData.COL_TYPEID + " integer,"
				+ WalletTransactionData.COL_PRICE + " real," 
				+ WalletTransactionData.COL_CLIENTID + " integer,"
				+ WalletTransactionData.COL_CLIENTNAME + " text,"
				+ WalletTransactionData.COL_STATION_ID + " integer,"		
				+ WalletTransactionData.COL_STATION_NAME + " text," 
				+ WalletTransactionData.COL_TRANSACTIONTYPE + " text,"
				+ WalletTransactionData.COL_TRANSACTIONFOR + " text,"		

				+ "UNIQUE (" + WalletTransactionData.COL_ID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createCharacterWalletJournalTable(SQLiteDatabase db)
	{
		String newTableQueryString = "create table " + WalletJournalData.TABLE + " ("
				
				+ WalletJournalData.COL_REF_ID + " integer primary key not null," 
				+ WalletJournalData.COL_DATE + " text," 
				+ WalletJournalData.COL_CHAR_ID + " integer," 
				+ WalletJournalData.COL_REF_TYPE + " text,"
				+ WalletJournalData.COL_OWNER_NAME1 + " text,"
				+ WalletJournalData.COL_OWNER_ID1 + " integer,"
				+ WalletJournalData.COL_OWNER_NAME2 + " text," 
				+ WalletJournalData.COL_OWNER_ID2 + " integer,"
				+ WalletJournalData.COL_ARG_NAME + " text,"
				+ WalletJournalData.COL_ARG_ID + " integer,"		
				+ WalletJournalData.COL_AMOUNT + " real," 
				+ WalletJournalData.COL_BALANCE + " real,"
				+ WalletJournalData.COL_REASON + " text,"
				+ WalletJournalData.COL_TAX_RECEIVER_ID + " integer,"	
				+ WalletJournalData.COL_TAX_AMOUNT + " real,"		

				+ "UNIQUE (" + WalletJournalData.COL_REF_ID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createCharacterAttributesTable(SQLiteDatabase db)
	{	
		String newTableQueryString = "create table " + AttributesData.TABLE + " ("
				
				+ AttributesData.COL_UNIQUE_ID + " integer primary key not null," 
				+ AttributesData.COL_CHAR_ID + " integer," 
				+ AttributesData.COL_IMPLANT_NAME + " text,"
				+ AttributesData.COL_IMPLANT_BONUS + " integer,"
				+ AttributesData.COL_IMPLANT_SLOT + " integer,"			

				+ "UNIQUE (" + AttributesData.COL_CHAR_ID + "," + AttributesData.COL_IMPLANT_SLOT + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createCharacterSheetTable(SQLiteDatabase db)
	{	
		String newTableQueryString = "create table " + CharacterSheetData.TABLE + " ("
				
				+ CharacterSheetData.COL_CHARACTER_ID + " integer primary key not null," 
				+ CharacterSheetData.COL_NAME + " text,"
				+ CharacterSheetData.COL_RACE + " text,"
				+ CharacterSheetData.COL_DOB + " text,"
				+ CharacterSheetData.COL_BLOODLINE + " text,"
				+ CharacterSheetData.COL_ANCESTRY + " text,"
				+ CharacterSheetData.COL_GENDER + " text,"
				+ CharacterSheetData.COL_CORP_NAME + " text,"
				+ CharacterSheetData.COL_CORP_ID + " integer,"
				+ CharacterSheetData.COL_ALLIANCE_ID + " integer,"
				+ CharacterSheetData.COL_ALLIANCE + " text,"		
				+ CharacterSheetData.COL_BALANCE + " real,"	
				
				+ CharacterSheetData.COL_INTELLIGENCE + " integer,"
				+ CharacterSheetData.COL_MEMORY + " integer,"
				+ CharacterSheetData.COL_CHARISMA + " integer,"
				+ CharacterSheetData.COL_PERCEPTION + " integer,"		
				+ CharacterSheetData.COL_WILLPOWER + " integer,"	

				+ "UNIQUE (" + CharacterSheetData.COL_CHARACTER_ID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createCharacterInfoTable(SQLiteDatabase db)
	{
		String newTableQueryString = "create table " + CharacterInfoData.TABLE + " ("
				
				+ CharacterInfoData.COL_CHARACTER_ID + " integer primary key not null," 
				+ CharacterInfoData.COL_RACE + " text," 
				+ CharacterInfoData.COL_BLOODLINE + " text,"
				+ CharacterInfoData.COL_WALLET_BALLANCE + " real,"
				+ CharacterInfoData.COL_SKILLPOINTS + " integer,"
				+ CharacterInfoData.COL_SHIP_NAME + " text,"
				+ CharacterInfoData.COL_SHIP_TYPEID + " integer,"
				+ CharacterInfoData.COL_CORP_DATE + " text,"
				+ CharacterInfoData.COL_ALLIANCE_ID + " integer,"
				+ CharacterInfoData.COL_ALLIANCE + " text,"		
				+ CharacterInfoData.COL_ALLIANCE_DATE + " text,"				
				+ CharacterInfoData.COL_LAST_KNOWN_LOC + " text,"				
				+ CharacterInfoData.COL_SEC_STATUS + " real,"				

				+ "UNIQUE (" + CharacterInfoData.COL_CHARACTER_ID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createSkillsTable(SQLiteDatabase db)
	{
		String newTableQueryString = "create table " + SkillsData.TABLE + " ("
				+ SkillsData.COL_UNIQUE_ID + " integer primary key not null," 
				+ SkillsData.COL_CHAR_ID + " integer," 
				+ SkillsData.COL_TYPEID + " integer,"
				+ SkillsData.COL_SKILLPOINTS + " integer,"
				+ SkillsData.COL_LEVEL + " integer,"
				+ SkillsData.COL_UNPUBLISHED + " integer,"
				+ "UNIQUE (" + SkillsData.COL_CHAR_ID + ", " + SkillsData.COL_TYPEID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createSkillTreeDataTable(SQLiteDatabase db)
	{
		String newTableQueryString = "create table " + SkillTreeData.TABLE + " ("
				+ SkillTreeData.COL_SKILL_TYPE_ID + " integer primary key not null," 
				+ SkillTreeData.COL_SKILL_NAME + " text," 
				+ SkillTreeData.COL_SKILL_DESCRIPTION + " text,"
				+ SkillTreeData.COL_SKILL_RANK + " integer,"
				+ SkillTreeData.COL_SKILL_PUBLISHED + " integer,"
				+ SkillTreeData.COL_SKILL_PRIM_ATTR + " integer,"
				+ SkillTreeData.COL_SKILL_SEC_ATTR + " integer,"
				+ SkillTreeData.COL_SKILL_PREREQUESITES + " text,"
				+ SkillTreeData.COL_SKILL_GROUP_ID + " integer,"
				+ SkillTreeData.COL_SKILL_GROUP_NAME + " text,"				
				+ "UNIQUE (" + SkillTreeData.COL_SKILL_TYPE_ID + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
	
	private static void createSkillQueueTable(SQLiteDatabase db)
	{		
		String newTableQueryString = "create table " + SkillQueueData.TABLE + " ("
				+ SkillQueueData.COL_UNIQUE_ID + " integer primary key not null," 
				+ SkillQueueData.COL_CHAR_ID + " integer," 
				+ SkillQueueData.COL_TYPEID + " integer,"
				+ SkillQueueData.COL_START_SP + " integer,"
				+ SkillQueueData.COL_END_SP + " integer,"
				+ SkillQueueData.COL_LEVEL + " integer,"
				+ SkillQueueData.COL_START_TIME + " text,"
				+ SkillQueueData.COL_END_TIME + " text,"
				+ SkillQueueData.COL_POSITION + " integer,"				
				+ "UNIQUE (" + SkillQueueData.COL_CHAR_ID + ", " + SkillQueueData.COL_POSITION + ") ON CONFLICT REPLACE);";

		db.execSQL(newTableQueryString);
	}
}
