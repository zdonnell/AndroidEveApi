package com.zdonnell.androideveapi.corporation.killlog;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.killlog.AbstractKillLogParser;

public class KillLogParser extends AbstractKillLogParser {
	private KillLogParser() {
		super(ApiPath.CORPORATION);
	}

	public static KillLogParser getInstance() {
		return new KillLogParser();
	}
}