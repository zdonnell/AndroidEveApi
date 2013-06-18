package com.zdonnell.androideveapi.corporation.member.security.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.member.security.ApiSecurityRole;
import com.zdonnell.androideveapi.corporation.member.security.log.ApiRoleHistory;
import com.zdonnell.androideveapi.corporation.member.security.log.MemberSecurityLogParser;
import com.zdonnell.androideveapi.corporation.member.security.log.MemberSecurityLogResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class SecurityLogParserTest extends FullAuthParserTest {
	public SecurityLogParserTest() {
		super(ApiPath.CORPORATION, ApiPage.MEMBER_SECURITY_LOG);
	}

	@Test
	public void getResponse() throws ApiException {
		MemberSecurityLogParser parser = MemberSecurityLogParser.getInstance();
		MemberSecurityLogResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<ApiRoleHistory> roleHistories = response.getAll();
		assertNotNull(roleHistories);
		assertEquals("Incorrect amount of role histories found.", 4, roleHistories.size());
		boolean found = false;
		for (ApiRoleHistory roleHistory : roleHistories) {
			if (roleHistory.getCharacterName().equals("Tester1")) {
				found = true;
				Set<ApiSecurityRole> oldRoles = roleHistory.getOldRoles();
				assertEquals("Incorrect amount of old roles found.", 8, oldRoles.size());
				boolean oldRoleFound = false;
				for (ApiSecurityRole securityRole : oldRoles) {
					if (securityRole.getRoleID() == 4194304) {
						oldRoleFound = true;
						assertEquals("Wrong old role name.", "roleHangarCanQuery3", securityRole.getRoleName());
					}
				}
				assertTrue("Test old role not found. ", oldRoleFound);
				Set<ApiSecurityRole> newRoles = roleHistory.getNewRoles();
				assertEquals("Incorrect amount of old roles found.", 0, newRoles.size());
			}
		}
		assertTrue("Test role history not found. ", found);
	}
}