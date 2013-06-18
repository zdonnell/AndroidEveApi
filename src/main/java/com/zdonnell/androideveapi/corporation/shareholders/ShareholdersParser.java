package com.zdonnell.androideveapi.corporation.shareholders;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class ShareholdersParser extends AbstractListParser<ShareholdersHandler, ShareholdersResponse, ApiShareholder> {
	public ShareholdersParser() {
		super(ShareholdersResponse.class, 2, ApiPath.CORPORATION, ApiPage.SHAREHOLDERS, ShareholdersHandler.class);
	}

	public static ShareholdersParser getInstance() {
		return new ShareholdersParser();
	}

	@Override
	public ShareholdersResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}