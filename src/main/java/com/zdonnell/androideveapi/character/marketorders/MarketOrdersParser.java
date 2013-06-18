package com.zdonnell.androideveapi.character.marketorders;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.marketorders.AbstractMarketOrdersParser;

public class MarketOrdersParser extends AbstractMarketOrdersParser {
	private MarketOrdersParser() {
		super(ApiPath.CHARACTER);
	}

	public static MarketOrdersParser getInstance() {
		return new MarketOrdersParser();
	}
}