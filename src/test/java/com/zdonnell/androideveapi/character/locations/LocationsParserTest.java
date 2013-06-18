package com.zdonnell.androideveapi.character.locations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.zdonnell.androideveapi.character.locations.LocationsParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.locations.AbstractLocationsParser;
import com.zdonnell.androideveapi.shared.locations.ApiLocation;
import com.zdonnell.androideveapi.shared.locations.LocationsResponse;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class LocationsParserTest extends FullAuthParserTest {
	public LocationsParserTest() {
		super(ApiPath.CHARACTER, ApiPage.LOCATIONS);
	}

	@Test
	public void getResponse() throws ApiException {
		AbstractLocationsParser parser = LocationsParser.getInstance();
		LocationsResponse response = parser.getResponse(auth, Arrays.asList(1002861698719L));
		assertNotNull(response);
		Collection<ApiLocation> locations = response.getAll();
		assertNotNull(locations);
		assertEquals(1, locations.size());
		ApiLocation location = locations.iterator().next();
		assertEquals(1002861698719L, location.getItemID());
		assertEquals("Caldari Control Tower Medium", location.getItemName());
		assertEquals(-896690626560L, location.getX());
		assertEquals(-163314032640L, location.getY());
		assertEquals(-1323431485440L, location.getZ());
	}
}