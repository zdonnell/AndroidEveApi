package com.zdonnell.androideveapi.shared.assetlist;

import java.util.LinkedHashSet;
import java.util.Set;

import com.zdonnell.androideveapi.core.ApiResponse;

public class AssetListResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;
	private final Set<EveAsset<EveAsset<?>>> assets = new LinkedHashSet<EveAsset<EveAsset<?>>>();

	public void add(EveAsset<EveAsset<?>> asset) {
		assets.add(asset);
	}

	public Set<EveAsset<EveAsset<?>>> getAll() {
		return assets;
	}
}