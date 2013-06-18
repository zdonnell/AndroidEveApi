package com.zdonnell.androideveapi.eve.errorlist;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class ErrorListHandler extends AbstractContentListHandler<ErrorListResponse, ApiErrorListItem> {
	public ErrorListHandler() {
		super(ErrorListResponse.class);
	}

	@Override
	protected ApiErrorListItem getItem(Attributes attrs) {
		ApiErrorListItem item = new ApiErrorListItem();
		item.setErrorText(getString(attrs, "errorText"));
		item.setErrorCode(getInt(attrs, "errorCode"));
		return item;
	}
}