package com.zdonnell.androideveapi.eve.reftypes;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class RefTypesHandler extends AbstractContentListHandler<RefTypesResponse, ApiRefType> {

	public RefTypesHandler() {
		super(RefTypesResponse.class);
	}

	@Override
	protected ApiRefType getItem(Attributes attrs) {
		ApiRefType item = new ApiRefType();
		item.setRefTypeID(getInt(attrs, "refTypeID"));
		item.setRefTypeName(getString(attrs, "refTypeName"));
		return item;
	}
}