package com.zdonnell.androideveapi.corporation.member.tracking;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MemberTrackingParser extends AbstractListParser<MemberTrackingHandler, MemberTrackingResponse, ApiMember> {
	public MemberTrackingParser() {
		super(MemberTrackingResponse.class, 2, ApiPath.CORPORATION, ApiPage.MEMBER_TRACKING,
				MemberTrackingHandler.class);
	}

	public static MemberTrackingParser getInstance() {
		return new MemberTrackingParser();
	}

	@Override
	public MemberTrackingResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}

	public MemberTrackingResponse getExtendedResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth, "extended", "1");
	}
}