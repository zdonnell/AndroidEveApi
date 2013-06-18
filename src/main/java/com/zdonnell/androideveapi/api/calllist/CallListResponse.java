package com.zdonnell.androideveapi.api.calllist;

import com.zdonnell.androideveapi.core.ApiResponse;

public class CallListResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;
	CallList callList;

	public CallList get() {
		return callList;
	}

	public void set(CallList callList) {
		this.callList = callList;
	}
}