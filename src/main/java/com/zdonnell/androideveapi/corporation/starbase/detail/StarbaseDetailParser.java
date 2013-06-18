package com.zdonnell.androideveapi.corporation.starbase.detail;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class StarbaseDetailParser extends AbstractApiParser<StarbaseDetailResponse> {
	public StarbaseDetailParser() {
		super(StarbaseDetailResponse.class, 2, ApiPath.CORPORATION, ApiPage.STARBASE_DETAIL);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new StarbaseDetailHandler();
	}

	public static StarbaseDetailParser getInstance() {
		return new StarbaseDetailParser();
	}

	public StarbaseDetailResponse getResponse(ApiAuth<?> auth, long itemID) throws ApiException {
		return super.getResponse(auth, "itemID", Long.toString(itemID));
	}
}
