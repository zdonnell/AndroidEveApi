package com.zdonnell.androideveapi.link.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zdonnell.androideveapi.shared.assetlist.EveAsset;

public class AssetsData {

	public final static String TABLE = "assets";
	
	public final static String COL_ID = "assets_item_id";
	public final static String COL_LOC_ID = "assets_location_id";
	public final static String COL_CHAR_ID = "assets_char_id";
	public final static String COL_PARENT_ID = "assets_parent_id";
	public final static String COL_TYPE_ID = "assets_type_id";
	public final static String COL_QUANTITY = "assets_quantity";
	public final static String COL_FLAG = "assets_flag";
	public final static String COL_SINGLETON = "assets_singleton";
	public final static String COL_RAW_QUANTITY = "assets_raw_quantity";
	
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
		
	public AssetsData(Context context) 
	{
		db = new Database.OpenHelper(context).getWritableDatabase();
	}
	
	/**	 
	 * Stores the provided assets in the database
	 *  
	 * @param characterID
	 * @param transactions
	 */
	public void setAssets(int characterID, Set<EveAsset<EveAsset<?>>> assets)
	{
		// Clearing out all the assets from the database is easier than checking row by row to see
		// which assets still exist in the newest API response
		db.delete(TABLE, COL_CHAR_ID + " = ?", new String[] { String.valueOf(characterID) });

		db.beginTransaction();
		
		for (EveAsset<EveAsset<?>> assetItem : assets) insertAsset(assetItem, characterID, null);
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	/**
	 * Inserts the provided asset and all of it's children into the database
	 * 
	 * @param assetItem
	 * @param insertValues
	 * @param parentItemID
	 */
	private void insertAsset(EveAsset<?> assetItem, int characterID, Long parentItemID)
	{		
		ContentValues insertValues = new ContentValues();
		
		insertValues.put(COL_ID, assetItem.getItemID());
		insertValues.put(COL_LOC_ID, assetItem.getLocationID());
		insertValues.put(COL_CHAR_ID, characterID);
		insertValues.put(COL_PARENT_ID, parentItemID);
		insertValues.put(COL_TYPE_ID, assetItem.getTypeID());
		insertValues.put(COL_QUANTITY, assetItem.getQuantity());
		insertValues.put(COL_FLAG, assetItem.getFlag());
		insertValues.put(COL_SINGLETON, assetItem.getSingleton() ? 1 : 0);
		insertValues.put(COL_RAW_QUANTITY, assetItem.getRawQuantity());
		
		db.insert(TABLE, null, insertValues);
		insertValues.clear();
		
		@SuppressWarnings("unchecked")
		Collection<EveAsset<?>> containedAssets = (Collection<EveAsset<?>>) assetItem.getAssets();
		for (EveAsset<?> containedAsset : containedAssets) insertAsset(containedAsset, characterID, assetItem.getItemID());
	}
	
	/**
	 * Returns a collection of the characters Eve Assets in the correct nested hierarchy
	 * 
	 * @param characterID
	 * @return
	 */
	public Collection<EveAsset<EveAsset<?>>> getAssets(int characterID)
	{
		Set<EveAsset<EveAsset<?>>> groupedAssets = new LinkedHashSet<EveAsset<EveAsset<?>>>();
		Map<Long, ArrayList<EveAsset<EveAsset<?>>>> childAssets = new HashMap<Long, ArrayList<EveAsset<EveAsset<?>>>>();
				
		Cursor c = db.query(TABLE, null, COL_CHAR_ID + " = ?", new String[] { String.valueOf(characterID) }, null, null, null);		
				
		int id_index = c.getColumnIndex(COL_ID);
		int loc_id_index = c.getColumnIndex(COL_LOC_ID);
		int type_id_index = c.getColumnIndex(COL_TYPE_ID);
		int parent_index = c.getColumnIndex(COL_PARENT_ID);
		int quantity_index = c.getColumnIndex(COL_QUANTITY);
		int flag_index = c.getColumnIndex(COL_FLAG);
		int singleton_index = c.getColumnIndex(COL_SINGLETON);
		int raw_quantity_index = c.getColumnIndex(COL_RAW_QUANTITY);
			
		while (c.moveToNext())
		{
			EveAsset<EveAsset<?>> asset = new EveAsset<EveAsset<?>>();
			
			asset.setItemID(c.getLong(id_index));
			asset.setLocationID(c.getLong(loc_id_index));
			asset.setTypeID(c.getInt(type_id_index));
			asset.setQuantity(c.getInt(quantity_index));
			asset.setFlag(c.getInt(flag_index));
			asset.setSingleton(c.getInt(singleton_index) == 1);
			asset.setRawQuantity(c.getInt(raw_quantity_index));
			
			Long parentID = c.getLong(parent_index);
			
			// If there is no parent ID, put it in the final root level set of assets
			if (parentID == 0) groupedAssets.add(asset);
			
			// Else put it in a list of assets mapped by it's parent ID to dig through on a second pass.
			else 
			{
				ArrayList<EveAsset<EveAsset<?>>> assetsInParent = childAssets.get(parentID);
				if (assetsInParent == null) assetsInParent = new ArrayList<EveAsset<EveAsset<?>>>();
				
				assetsInParent.add(asset);
				childAssets.put(parentID, assetsInParent);
			}
		}
			
		for (EveAsset<EveAsset<?>> asset : groupedAssets) findChildren(asset, childAssets);
				
		c.close();
		
		return groupedAssets;
	}
	
	/**
	 * Finds the children of a Given asset in a set of asset lists and adds them to the provided
	 * asset.
	 * 
	 * @param asset an EveAsset for which to find the child assets of.
	 * @param childAssetLists A collection of Lists representing contained assets, mapped by the asset ID they belong to
	 */
	private void findChildren(EveAsset<EveAsset<?>> asset, Map<Long, ArrayList<EveAsset<EveAsset<?>>>> childAssetLists)
	{
		if (childAssetLists.get(asset.getItemID()) == null) return;
		else
		{
			for (EveAsset<EveAsset<?>> childAsset : childAssetLists.get(asset.getItemID()))
			{
				findChildren(childAsset, childAssetLists);
				asset.add(childAsset);
			}
		}
	}
}
