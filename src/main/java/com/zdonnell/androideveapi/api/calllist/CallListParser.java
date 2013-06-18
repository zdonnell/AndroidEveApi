package com.zdonnell.androideveapi.api.calllist;

import com.zdonnell.androideveapi.core.AbstractApiParser;
import com.zdonnell.androideveapi.core.AbstractContentHandler;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class CallListParser extends AbstractApiParser<CallListResponse> {
	public CallListParser() {
		super(CallListResponse.class, 2, ApiPath.API, ApiPage.CALL_LIST);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new CallListHandler();
	}

//	@Override
//	protected Digester getDigester() {
//		Digester digester = super.getDigester();
//		digester.addObjectCreate("eveapi/result", CallList.class);
//
//		digester.addFactoryCreate("eveapi/result/rowset/row", new AbstractObjectCreationFactory() {
//			@Override
//			public Object createObject(Attributes attributes) throws Exception {
//				if (attributes.getValue("accessMask") != null) {
//					return new Call();
//				} else {
//					return new CallGroup();
//				}
//			}
//		});
//		digester.addSetProperties("eveapi/result/rowset/row");
//		digester.addSetNext("eveapi/result/rowset/row", "add");
//		digester.addSetNext("eveapi/result", "set");
//
//		return digester;
//	}

	public static CallListParser getInstance() {
		return new CallListParser();
	}

	@Override
	public CallListResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}