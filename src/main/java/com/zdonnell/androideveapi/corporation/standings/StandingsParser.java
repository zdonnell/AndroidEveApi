package com.zdonnell.androideveapi.corporation.standings;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.standings.AbstractStandingsParser;

public class StandingsParser extends AbstractStandingsParser {
	private StandingsParser() {
		super(ApiPath.CORPORATION);
	}

	public static StandingsParser getInstance() {
		return new StandingsParser();
	}
}