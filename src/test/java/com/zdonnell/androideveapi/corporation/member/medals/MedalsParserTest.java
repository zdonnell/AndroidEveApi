package com.zdonnell.androideveapi.corporation.member.medals;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.member.medals.MemberMedal;
import com.zdonnell.androideveapi.corporation.member.medals.MemberMedalsParser;
import com.zdonnell.androideveapi.corporation.member.medals.MemberMedalsResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class MedalsParserTest extends FullAuthParserTest {
	public MedalsParserTest() {
		super(ApiPath.CORPORATION, ApiPage.MEMBER_MEDALS);
	}

	@Test
	public void getResponse() throws ApiException {
		MemberMedalsParser parser = MemberMedalsParser.getInstance();
		MemberMedalsResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<MemberMedal> memberMedals = response.getAll();
		assertNotNull(memberMedals);
		assertEquals(45, memberMedals.size());
		MemberMedal memberMedal = memberMedals.iterator().next();
		assertEquals(1745, memberMedal.getMedalID());
		assertEquals(264288979L, memberMedal.getCharacterID());
		assertEquals("Ooy late night op", memberMedal.getReason());
		assertTrue("Should have been public", memberMedal.isPublic());
		assertEquals(817217271L, memberMedal.getIssuerID());
		assertDate(2008, 11, 12, 7, 39, 28, memberMedal.getIssued());
	}
}