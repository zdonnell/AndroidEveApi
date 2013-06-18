package com.zdonnell.androideveapi.eve.factwar.stats.top;

public class CharacterKills extends CharacterStat implements KillStat {
	private int kills;

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
}