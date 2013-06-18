package com.zdonnell.androideveapi.character.factwar.stats;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;


import org.junit.Test;


import com.zdonnell.androideveapi.character.facwar.stats.FacWarStatsParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.facwar.stats.FacWarStatsResponse;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class FacWarStatsParserTest extends FullAuthParserTest {
	public FacWarStatsParserTest() {
		super(ApiPath.CHARACTER, ApiPage.FACT_WAR_STATS);
	}

	@Test
	public void getResponse() throws ApiException {
		FacWarStatsParser parser = FacWarStatsParser.getInstance();
		FacWarStatsResponse facWarStats = parser.getResponse(auth);
		assertEquals(500001, facWarStats.getFactionID());
		assertEquals("Caldari State", facWarStats.getFactionName());
		assertDate(2008, 6, 10, 22, 10, 0, facWarStats.getEnlisted());
		assertEquals(4, facWarStats.getCurrentRank());
		assertEquals(4, facWarStats.getHighestRank());
		assertEquals(0, facWarStats.getKillsYesterday());
		assertEquals(0, facWarStats.getKillsLastWeek());
		assertEquals(0, facWarStats.getKillsTotal());
		assertEquals(0, facWarStats.getVictoryPointsYesterday());
		assertEquals(1044, facWarStats.getVictoryPointsLastWeek());
		assertEquals(0, facWarStats.getVictoryPointsTotal());
	}
}