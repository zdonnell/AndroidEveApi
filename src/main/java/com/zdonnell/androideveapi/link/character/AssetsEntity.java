package com.zdonnell.androideveapi.link.character;

import java.util.ArrayList;

public abstract class AssetsEntity {
	/**
	 * This boolean value represents whether the the AssetsEntity contains assets within it.
	 * If the AssetsEntity is a container, but does not have anything inside, this value will
	 * still be <B>false</B>.
	 */
	protected boolean containsAssets = false;
	
	private ArrayList<AssetsEntity> nestedAssets = new ArrayList<AssetsEntity>();
	
	private AssetAttributes attributes;
	
	/**
	 * Raw asset lists are typically parsed recursively, thus if an AssetsEntity has contained
	 * assets, they will be parsed first.  This constructor will allow an AssetsEntity to be provided
	 * it's contained assets.
	 * 
	 * @param attributes the attributes of the AssetsEntity in the form of an {@link AssetAttributes}
	 * @param containedAssets An {@link ArrayList} of {@link AssetsEntity} objects.  Or null, if the Asset does not contain
	 * any assets.
	 */
	public AssetsEntity(ArrayList<AssetsEntity> containedAssets)
	{		
		if (containedAssets != null && containedAssets.size() != 0) { 
			this.containsAssets = true;
			this.nestedAssets = containedAssets;
		}
	}
	
	/**
	 * @return whether the AssetsEntity has containedAssets
	 * @see #isContainer
	 */
	public boolean containsAssets() { return containsAssets; }
	
	public ArrayList<AssetsEntity> getContainedAssets() { return nestedAssets; }
	
	public AssetAttributes attributes() { return attributes; }
	
	
	public static class Station extends AssetsEntity
	{
		private int locationID;
		
		public Station(ArrayList<AssetsEntity> containedAssets, int locationID) {
			super(containedAssets);
			this.containsAssets = true;
			this.locationID = locationID;
		}
		
		public int getLocationID() { return locationID; }
	}
	
	public static class Item extends AssetsEntity
	{		
		public Item(ArrayList<AssetsEntity> containedAssets, AssetAttributes attributes) {
			super(containedAssets);	
			super.attributes = attributes;
		}
	}
	
	public static class AssetAttributes {
		public int typeID;
		public int flag;
		public int quantity;
		public int locationID;
		public boolean singleton;
	}
}


/*package com.zdonnell.eve.api.character;

import java.util.ArrayList;

public class AssetsEntity {
	
	/**
	 * This boolean value represents whether the the AssetsEntity contains assets within it.
	 * If the AssetsEntity is a container, but does not have anything inside, this value will
	 * still be <B>false</B>.
	 *
	private boolean isContainer = false;
	
	private ArrayList<AssetsEntity> nestedAssets = new ArrayList<AssetsEntity>();
	
	private AssetAttributes attributes;
	
	/**
	 * Raw asset lists are typically parsed recursively, thus if an AssetsEntity has contained
	 * assets, they will be parsed first.  This constructor will allow an AssetsEntity to be provided
	 * it's contained assets.
	 * 
	 * @param attributes the attributes of the AssetsEntity in the form of an {@link AssetAttributes}
	 * @param containedAssets An {@link ArrayList} of {@link AssetsEntity} objects.  Or null, if the Asset does not contain
	 * any assets.
	 *
	public AssetsEntity(AssetAttributes attributes, ArrayList<AssetsEntity> containedAssets)
	{
		this.attributes = attributes;
		
		if (containedAssets != null && containedAssets.size() != 0) { 
			this.isContainer = true;
			this.nestedAssets = containedAssets;
		}
	}
		
	/**
	 * @return whether the AssetsEntity has containedAssets
	 * @see #isContainer
	 *
	public boolean hasContainedAssets() { return isContainer; }
	
	public ArrayList<AssetsEntity> containedAssets() { return nestedAssets; }
	
	public AssetAttributes attributes() { return attributes; }
	
	
	public static class AssetAttributes {
		public int typeID;
		public int flag;
		public int quantity;
		public int locationID = -1;
	}
	
	public static class AssetLocation {
		private int locationID;
		private ArrayList<AssetsEntity> containedAssets;
		
		public AssetLocation(int locationID, ArrayList<AssetsEntity> containedAssets)
		{
			this.locationID = locationID;
			this.containedAssets = containedAssets;
		}
		
		public int getLocationID() { return locationID; }
		public ArrayList<AssetsEntity> getContainedAssets() { return containedAssets; }
		
		public void setContainedAssets(ArrayList<AssetsEntity> assetsList) { this.containedAssets = assetsList; }
	}
}*/
