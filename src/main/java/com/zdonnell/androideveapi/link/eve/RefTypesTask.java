package com.zdonnell.androideveapi.link.eve;

import android.content.Context;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.eve.reftypes.ApiRefType;
import com.zdonnell.androideveapi.eve.reftypes.RefTypesParser;
import com.zdonnell.androideveapi.eve.reftypes.RefTypesResponse;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.link.ApiCachingTask;
import com.zdonnell.androideveapi.link.ApiExceptionCallback;
import com.zdonnell.androideveapi.link.database.RefTypesData;

/**
 * 
 * @author Zach
 *
 */
public class RefTypesTask extends ApiCachingTask<Void, Void, RefTypesResponse> {		
	public RefTypesTask(ApiExceptionCallback<RefTypesResponse> callback, final Context context) {						
		super(callback, context, true, null, new EveApiInteraction<RefTypesResponse>() {
			public RefTypesResponse perform() throws ApiException {
				RefTypesParser parser = RefTypesParser.getInstance();
				RefTypesResponse response = parser.getResponse();
				
				RefTypesData refTypesData = new RefTypesData(context);
				refTypesData.setRefTypes(response.getAll());

				return response;
			}
		});
	}

	@Override
	public int requestTypeHash() {
		return ApiPath.EVE.getPath().concat(ApiPage.REF_TYPES.getPage()).hashCode();
	}
	
	@Override
	public RefTypesResponse buildResponseFromDatabase() {
		RefTypesResponse response = new RefTypesResponse();
		
		RefTypesData refTypes = new RefTypesData(context);
		for (ApiRefType refType : refTypes.getRefTypes()) response.add(refType);
		
		return response;
	}
}