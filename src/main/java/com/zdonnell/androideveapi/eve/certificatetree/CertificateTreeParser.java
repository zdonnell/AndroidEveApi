package com.zdonnell.androideveapi.eve.certificatetree;

import com.zdonnell.androideveapi.core.AbstractListParser;
import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.exception.ApiException;

public class CertificateTreeParser extends AbstractListParser<CertificateTreeHandler, CertificateTreeResponse, ApiCertificateCategory> {
	public CertificateTreeParser() {
		super(CertificateTreeResponse.class, 2, ApiPath.EVE, ApiPage.CERTIFICATE_TREE, CertificateTreeHandler.class);
	}

	public static CertificateTreeParser getInstance() {
		return new CertificateTreeParser();
	}

	@Override
	public CertificateTreeResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}