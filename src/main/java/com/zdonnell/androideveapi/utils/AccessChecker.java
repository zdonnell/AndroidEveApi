package com.zdonnell.androideveapi.utils;

import java.util.HashSet;
import java.util.Set;

import com.zdonnell.androideveapi.account.apikeyinfo.ApiKeyInfoResponse;
import com.zdonnell.androideveapi.api.calllist.Call;
import com.zdonnell.androideveapi.api.calllist.CallList;

public class AccessChecker {
	public static Set<Call> getCalls(ApiKeyInfoResponse apiKeyInfo, CallList callList) {
		Set<Call> result = new HashSet<Call>();
		for (Call call : callList.getCalls()) {
			long cam = call.getAccessMask();
			if((apiKeyInfo.getAccessMask() & cam) == cam && apiKeyInfo.getType() == call.getType())
				result.add(call);
		}
		return result;
	}
}