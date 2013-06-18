package com.zdonnell.androideveapi.corporation.outpost.servicedetail;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class OutpostServiceDetailParser extends AbstractListParser<OutpostServiceDetailHandler, OutpostServiceDetailResponse, ApiOutpostServiceDetail> {
	public OutpostServiceDetailParser() {
		super(OutpostServiceDetailResponse.class, 2, ApiPath.CORPORATION, ApiPage.OUTPOST_SERVICE_DETAIL, OutpostServiceDetailHandler.class);
	}

	public static OutpostServiceDetailParser getInstance() {
		return new OutpostServiceDetailParser();
	}

	@Override
	public OutpostServiceDetailResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}