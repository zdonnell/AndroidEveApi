package com.zdonnell.androideveapi.corporation.sheet;


import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class CorpSheetParser extends AbstractApiParser<CorpSheetResponse> {
	public CorpSheetParser() {
		super(CorpSheetResponse.class, 2, ApiPath.CORPORATION, ApiPage.CORPORATION_SHEET);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new CorpSheetHandler();
	}

	public static CorpSheetParser getInstance() {
		return new CorpSheetParser();
	}

	@Override
	public CorpSheetResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}

	public CorpSheetResponse getResponse(long corporationID) throws ApiException {
		return getResponse("corporationID", Long.toString(corporationID));
	}
}