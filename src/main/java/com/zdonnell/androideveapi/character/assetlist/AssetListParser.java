package com.zdonnell.androideveapi.character.assetlist;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.assetlist.AbstractAssetListParser;

public class AssetListParser extends AbstractAssetListParser {
	private AssetListParser() {
		super(ApiPath.CHARACTER);
	}

	public static AssetListParser getInstance() {
		return new AssetListParser();
	}
}