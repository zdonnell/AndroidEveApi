package com.zdonnell.androideveapi.corporation.member.security.log;


import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MemberSecurityLogParser extends AbstractListParser<MemberSecurityLogHandler, MemberSecurityLogResponse, ApiRoleHistory> {
	public MemberSecurityLogParser() {
		super(MemberSecurityLogResponse.class, 2, ApiPath.CORPORATION, ApiPage.MEMBER_SECURITY_LOG, MemberSecurityLogHandler.class);
	}

	public static MemberSecurityLogParser getInstance() {
		return new MemberSecurityLogParser();
	}

	@Override
	public MemberSecurityLogResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}