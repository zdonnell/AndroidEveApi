package com.zdonnell.androideveapi.eve.errorlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.errorlist.ApiErrorListItem;
import com.zdonnell.androideveapi.eve.errorlist.ErrorListParser;
import com.zdonnell.androideveapi.eve.errorlist.ErrorListResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.NoAuthParserTest;

public class ErrorListParserTest extends NoAuthParserTest {
	public ErrorListParserTest() {
		super(ApiPath.EVE, ApiPage.ERROR_LIST);
	}

	@Test
	public void getResponse() throws ApiException {
		ErrorListParser parser = ErrorListParser.getInstance();
		ErrorListResponse response = parser.getResponse();
		assertNotNull(response);
		Collection<ApiErrorListItem> errorListItems = response.getAll();
		assertEquals(61, errorListItems.size());
		boolean found = false;
		for (ApiErrorListItem apiErrorListItem : errorListItems) {
			if (apiErrorListItem.getErrorCode() == 110) {
				found = true;
				assertEquals("Invalid beforeTransID provided.", apiErrorListItem.getErrorText());
			}
		}
		assertTrue("test apiErrorListItem wasn't found.", found);
	}
}