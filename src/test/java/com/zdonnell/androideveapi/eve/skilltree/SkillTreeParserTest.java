package com.zdonnell.androideveapi.eve.skilltree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkill;
import com.zdonnell.androideveapi.eve.skilltree.ApiSkillGroup;
import com.zdonnell.androideveapi.eve.skilltree.CharacterAttribute;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeParser;
import com.zdonnell.androideveapi.eve.skilltree.SkillTreeResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.NoAuthParserTest;

public class SkillTreeParserTest extends NoAuthParserTest {
	public SkillTreeParserTest() {
		super(ApiPath.EVE, ApiPage.SKILL_TREE);
	}

	@Test
	public void getResponse() throws ApiException {
		SkillTreeParser parser = SkillTreeParser.getInstance();
		SkillTreeResponse response = parser.getResponse();
		assertNotNull("Should have returned a response.", response);
		assertEquals("version 2 expected.", 2, response.getVersion());
		assertNotNull("Response should contain the current time.", response.getCurrentTime());
		assertNotNull("Response should contain the time untill this response data is cached.", response.getCachedUntil());
		assertTrue("Should return some skill groups.", response.getAll().size() > 0);
		Collection<ApiSkillGroup> skillGroups = response.getAll();
		assertEquals(17, skillGroups.size());
		ApiSkillGroup skillGroup = skillGroups.iterator().next();
		assertEquals("Corporation Management", skillGroup.getGroupName());
		assertEquals(266, skillGroup.getGroupID());
		Collection<ApiSkill> skills = skillGroup.getSkills();
		assertEquals(15, skills.size());
		Iterator<ApiSkill> iterator = skills.iterator();
		ApiSkill skill = iterator.next();
		assertEquals(11584, skill.getTypeID());
		assertEquals("Anchoring", skill.getTypeName());
		assertTrue(skill.isPublished());
		skill = iterator.next();
		assertEquals(3369, skill.getTypeID());
		assertEquals("CFO Training", skill.getTypeName());
		assertFalse(skill.isPublished());
		assertEquals(CharacterAttribute.MEMORY, skill.getPrimaryAttribute());
		assertEquals(CharacterAttribute.CHARISMA, skill.getSecondaryAttribute());

	}
}