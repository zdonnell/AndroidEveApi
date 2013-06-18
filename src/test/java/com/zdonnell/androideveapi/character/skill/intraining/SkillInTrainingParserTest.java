package com.zdonnell.androideveapi.character.skill.intraining;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;


import com.zdonnell.androideveapi.character.skill.intraining.SkillInTrainingParser;
import com.zdonnell.androideveapi.character.skill.intraining.SkillInTrainingResponse;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class SkillInTrainingParserTest extends FullAuthParserTest {
	public SkillInTrainingParserTest() {
		super(ApiPath.CHARACTER, ApiPage.SKILL_IN_TRAINING);
	}

	@Test
	public void getResponse() throws ApiException {
		SkillInTrainingParser parser = SkillInTrainingParser.getInstance();
		SkillInTrainingResponse response = parser.getResponse(auth);
		assertNotNull(response);
		assertDate(2008, 8, 17, 6, 43, 0, response.getCurrentTQTime());
		assertDate(2008, 8, 17, 15, 29, 44, response.getTrainingEndTime());
		assertDate(2008, 8, 15, 4, 1, 16, response.getTrainingStartTime());
		assertEquals(3305, response.getTrainingTypeID());
		assertEquals(24000, response.getTrainingStartSP());
		assertEquals(135765, response.getTrainingDestinationSP());
		assertEquals(4, response.getTrainingToLevel());
		assertEquals(true, response.isSkillInTraining());
	}
}