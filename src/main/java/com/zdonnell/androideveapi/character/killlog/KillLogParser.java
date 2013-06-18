package com.zdonnell.androideveapi.character.killlog;

import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.shared.killlog.AbstractKillLogParser;

public class KillLogParser extends AbstractKillLogParser {
	private KillLogParser() {
		super(ApiPath.CHARACTER);
	}

	public static KillLogParser getInstance() {
		return new KillLogParser();
	}
}