package com.zdonnell.androideveapi.eve.certificatetree;

import com.zdonnell.androideveapi.core.ApiListResponse;

public class CertificateTreeResponse extends ApiListResponse<ApiCertificateCategory> {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		String result = "";
		for (ApiCertificateCategory certificateCategory : getAll())
			result += certificateCategory + "\n";
		return result;
	}
}