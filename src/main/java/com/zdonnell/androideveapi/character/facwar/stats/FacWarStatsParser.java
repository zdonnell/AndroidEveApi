package com.zdonnell.androideveapi.character.facwar.stats;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.facwar.stats.AbstractFacWarStatsParser;

public class FacWarStatsParser extends AbstractFacWarStatsParser {
	private FacWarStatsParser() {
		super(ApiPath.CHARACTER);
	}

	public static FacWarStatsParser getInstance() {
		return new FacWarStatsParser();
	}
}