package com.zdonnell.androideveapi.character.research;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.character.research.ApiResearchAgent;
import com.zdonnell.androideveapi.character.research.ResearchParser;
import com.zdonnell.androideveapi.character.research.ResearchResponse;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class ResearchParserTest extends FullAuthParserTest {
	public ResearchParserTest() {
		super(ApiPath.CHARACTER, ApiPage.RESEARCH);
	}

	@Test
	public void getResponse() throws ApiException {
		ResearchParser parser = ResearchParser.getInstance();
		ResearchResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<ApiResearchAgent> researchAgents = response.getAll();
		assertNotNull(researchAgents);
		assertEquals(5, researchAgents.size());
		boolean found = false;
		for (ApiResearchAgent researchAgent : researchAgents) {
			if (researchAgent.getAgentID() == 3012659) {
				found = true;
				assertEquals(11452, researchAgent.getSkillTypeID());
				assertDate(2009, 10, 14, 21, 37, 42, researchAgent.getResearchStartDate());
				assertEquals(111.78, researchAgent.getPointsPerDay(), 0.0001);
				assertEquals(40.9576281249902, researchAgent.getRemainderPoints(), 1E-15);
				break;
			}
		}
		assertTrue("Test agent wasn't found.", found);
	}
}