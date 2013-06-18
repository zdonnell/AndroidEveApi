package com.zdonnell.androideveapi.corporation.outpost.servicedetail;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class OutpostServiceDetailHandler extends AbstractContentListHandler<OutpostServiceDetailResponse, ApiOutpostServiceDetail> {
	public OutpostServiceDetailHandler() {
		super(OutpostServiceDetailResponse.class);
	}

	@Override
	protected ApiOutpostServiceDetail getItem(Attributes attrs) {
		ApiOutpostServiceDetail item = new ApiOutpostServiceDetail();
		item.setStationID(getInt(attrs, "stationID"));
		item.setOwnerID(getLong(attrs, "ownerID"));
		item.setServiceName(getString(attrs, "serviceName")); 
		item.setMinStanding(getDouble(attrs, "minStanding")); 
		item.setSurchargePerBadStanding(getDouble(attrs, "surchargePerBadStanding")); 
		item.setDiscountPerGoodStanding(getDouble(attrs, "discountPerGoodStanding"));
		return item;
	}
}