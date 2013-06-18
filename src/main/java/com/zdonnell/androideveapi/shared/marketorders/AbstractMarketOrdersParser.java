package com.zdonnell.androideveapi.shared.marketorders;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public abstract class AbstractMarketOrdersParser extends AbstractListParser<MarketOrdersHandler, MarketOrdersResponse, ApiMarketOrder> {
	protected AbstractMarketOrdersParser(ApiPath path) {
		super(MarketOrdersResponse.class, 2, path, ApiPage.MARKET_ORDERS, MarketOrdersHandler.class);
	}

	@Override
	public MarketOrdersResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}