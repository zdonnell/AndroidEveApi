package com.zdonnell.androideveapi.map.factwar.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.map.factwar.systems.ApiFacWarSystem;
import com.zdonnell.androideveapi.map.factwar.systems.FacWarSystemsParser;
import com.zdonnell.androideveapi.map.factwar.systems.FacWarSystemsResponse;
import com.zdonnell.androideveapi.utils.NoAuthParserTest;

public class FacWarSystemsParserTest extends NoAuthParserTest {
	public FacWarSystemsParserTest() {
		super(ApiPath.MAP, ApiPage.FACTION_WAR_SYSTEMS);
	}

	@Test
	public void getResponse() throws ApiException {
		FacWarSystemsParser parser = FacWarSystemsParser.getInstance();
		FacWarSystemsResponse response = parser.getResponse();
		Set<ApiFacWarSystem> systems = response.getAll();
		assertEquals(171, systems.size());
		Iterator<ApiFacWarSystem> iterator = systems.iterator();
		ApiFacWarSystem facWarSystem = iterator.next();
		assertEquals(30002057, facWarSystem.getSolarSystemID());
		assertEquals("Hadozeko", facWarSystem.getSolarSystemName());
		assertEquals(0, facWarSystem.getOccupyingFactionID());
		assertEquals("", facWarSystem.getOccupyingFactionName());
		assertFalse("This system shouldn't be contested.", facWarSystem.isContested());
		for (int i = 0; i < 92; i++)
			iterator.next();
		facWarSystem = iterator.next();
		assertEquals(30045344, facWarSystem.getSolarSystemID());
		assertEquals("Nennamaila", facWarSystem.getSolarSystemName());
		assertEquals(500001, facWarSystem.getOccupyingFactionID());
		assertEquals("Caldari State", facWarSystem.getOccupyingFactionName());
		assertTrue("This system shouldn't be contested.", facWarSystem.isContested());
	}
}