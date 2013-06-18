package com.zdonnell.androideveapi.corporation.medals;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.medals.CorpMedal;
import com.zdonnell.androideveapi.corporation.medals.CorpMedalsResponse;
import com.zdonnell.androideveapi.corporation.medals.MedalsParser;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class MedalsParserTest extends FullAuthParserTest {
	public MedalsParserTest() {
		super(ApiPath.CORPORATION, ApiPage.MEDALS);
	}

	@Test
	public void getResponse() throws ApiException {
		MedalsParser parser = MedalsParser.getInstance();
		CorpMedalsResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<CorpMedal> medals = response.getAll();
		assertEquals("Incorrect amount of members found.", 18, medals.size());
		CorpMedal medal = medals.iterator().next();
		assertEquals("Wrong medal characterID", 1745, medal.getMedalID());
		assertEquals("Wrong medal name", "Capital Red Eyed Award", medal.getTitle());
		assertEquals(
				"Wrong medal description",
				"This award is given to captial pilots that not only fought on the front lines but stayed up way to late and past their 9pm bedtimes to kill the scum that think they are better than us.",
				medal.getDescription());
		assertEquals("Wrong member name", 817217271L, medal.getCreatorID());
		assertDate(2008, 11, 12, 7, 37, 0, medal.getCreated());
	}
}