package com.zdonnell.androideveapi.map.jumps;

import org.xml.sax.Attributes;

import com.zdonnell.androideveapi.core.AbstractContentListHandler;

public class JumpsHandler extends AbstractContentListHandler<JumpsResponse, ApiSystemJumps> {
	public JumpsHandler() {
		super(JumpsResponse.class);
	}

	@Override
	protected ApiSystemJumps getItem(Attributes attrs) {
		ApiSystemJumps item = new ApiSystemJumps();
		item.setSolarSystemID(getInt(attrs, "solarSystemID"));
		item.setShipJumps(getInt(attrs, "shipJumps"));
		return item;
	}
}