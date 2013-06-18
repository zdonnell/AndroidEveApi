package com.zdonnell.androideveapi.eve.typename;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.StringUtils;

public class TypeNameParser extends AbstractListParser<TypeNameHandler, TypeNameResponse, EveTypeName> {

	public TypeNameParser() {
		super(TypeNameResponse.class, 2, ApiPath.EVE, ApiPage.TYPE_NAME, TypeNameHandler.class);
	}
	
	public static TypeNameParser getInstance() {
		return new TypeNameParser();
	}

	public TypeNameResponse getResponse(int... arguments) throws ApiException {
		return super.getResponse("ids", StringUtils.join(",", arguments));
	}
}