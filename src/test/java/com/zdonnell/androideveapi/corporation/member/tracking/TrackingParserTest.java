package com.zdonnell.androideveapi.corporation.member.tracking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.member.tracking.ApiMember;
import com.zdonnell.androideveapi.corporation.member.tracking.MemberTrackingParser;
import com.zdonnell.androideveapi.corporation.member.tracking.MemberTrackingResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.utils.ExchangeProcessor;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;
import com.zdonnell.androideveapi.utils.MockApi;

public class TrackingParserTest extends FullAuthParserTest {
	public TrackingParserTest() {
		super(ApiPath.CORPORATION, ApiPage.MEMBER_TRACKING);
	}

	@Test
	public void getResponse() throws ApiException {
		MemberTrackingParser parser = MemberTrackingParser.getInstance();
		MemberTrackingResponse response = parser.getResponse(auth);
		assertNotNull(response);
		Set<ApiMember> members = response.getAll();
		boolean found = false;
		for (ApiMember member : members) {
			if(member.getCharacterID()==150336922L) {
				found = true;
				assertEquals("corpexport", member.getName());
				assertNull(member.getLocation());
			}
		}
		assertTrue("Test character not found: ", found);
	}

	@Test
	public void getExtendedResponse() throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(extended);
		context.start();
		MemberTrackingParser parser = MemberTrackingParser.getInstance();
		MemberTrackingResponse response = parser.getExtendedResponse(auth);
		assertNotNull(response);
		Set<ApiMember> members = response.getAll();
		boolean found = false;
		for (ApiMember member : members) {
			if(member.getCharacterID()==150336922L) {
				found = true;
				assertEquals("corpexport", member.getName());
				assertEquals("Bourynes VII - Moon 2 - University of Caille School", member.getLocation());
			}
		}
		assertTrue("Test character not found: ", found);
	}
	
	private RouteBuilder extended = new RouteBuilder() {
		@Override
		public void configure() {
			from("jetty:" + MockApi.URL + path.getPath() + "/" + page.getPage() + ".xml.aspx")
					.process(new ExchangeProcessor(
						new ExchangeProcessor.ExtraAsserts() {
							public void extraAsserts(Map<String, String> params) {
								assertNotNull(params);
								assertEquals("1", params.get("extended"));
							}
						},
						path.getPath() + "/" + page.getPage() + "Extended.xml"
					))
					.end();
		}
	};
}