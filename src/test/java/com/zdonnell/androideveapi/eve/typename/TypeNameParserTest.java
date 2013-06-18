package com.zdonnell.androideveapi.eve.typename;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.typename.EveTypeName;
import com.zdonnell.androideveapi.eve.typename.TypeNameParser;
import com.zdonnell.androideveapi.eve.typename.TypeNameResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.NoAuthParserTest;

public class TypeNameParserTest extends NoAuthParserTest {
	public TypeNameParserTest() {
		super(ApiPath.EVE, ApiPage.TYPE_NAME);
	}

	@Test
	public void getResponse() throws ApiException {
		TypeNameParser parser = TypeNameParser.getInstance();
		TypeNameResponse response = parser.getResponse(12345);
		Collection<EveTypeName> chars = response.getAll();
		assertEquals(1, chars.size());
		EveTypeName typeName = chars.iterator().next();
		assertEquals("200mm Railgun I Blueprint", typeName.getTypeName());
		assertEquals(12345, typeName.getTypeID());
	}

	@Override
	public void extraAsserts(Map<String, String> req) {
		super.extraAsserts(req);
		assertEquals("12345", req.get("ids"));
	}
}