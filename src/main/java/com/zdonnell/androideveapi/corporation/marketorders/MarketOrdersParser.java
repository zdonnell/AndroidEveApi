package com.zdonnell.androideveapi.corporation.marketorders;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.marketorders.AbstractMarketOrdersParser;

public class MarketOrdersParser extends AbstractMarketOrdersParser {
	private MarketOrdersParser() {
		super(ApiPath.CORPORATION);
	}

	public static MarketOrdersParser getInstance() {
		return new MarketOrdersParser();
	}
}