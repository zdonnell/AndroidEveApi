package com.zdonnell.androideveapi.corporation.member.medals;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiAuth;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class MemberMedalsParser extends AbstractListParser<MemberMedalsHandler, MemberMedalsResponse, MemberMedal> {
	public MemberMedalsParser() {
		super(MemberMedalsResponse.class, 2, ApiPath.CORPORATION, ApiPage.MEMBER_MEDALS, MemberMedalsHandler.class);
	}

	public static MemberMedalsParser getInstance() {
		return new MemberMedalsParser();
	}

	@Override
	public MemberMedalsResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}