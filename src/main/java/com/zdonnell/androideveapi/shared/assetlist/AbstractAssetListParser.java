package com.zdonnell.androideveapi.shared.assetlist;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractAssetListParser extends AbstractApiParser<AssetListResponse> {
	protected AbstractAssetListParser(ApiPath path) {
		super(AssetListResponse.class, 2, path, ApiPage.ASSET_LIST);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new AssetListHandler();
	}

//	@Override
//	protected Digester getDigester() {
//		Digester digester = super.getDigester();
//		digester.addObjectCreate("*/rowset/row", EveAsset.class);
//		digester.addSetProperties("*/rowset/row");
//		digester.addSetNext("*/rowset/row", "add");
//		return digester;
//	}

	@Override
	public AssetListResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}