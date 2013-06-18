package com.zdonnell.androideveapi.corporation.assetlist;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.assetlist.AssetListParser;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.assetlist.AbstractAssetListParser;
import com.zdonnell.androideveapi.shared.assetlist.AssetListResponse;
import com.zdonnell.androideveapi.shared.assetlist.EveAsset;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

@SuppressWarnings("rawtypes")
public class AssetListParserTest extends FullAuthParserTest {
	public AssetListParserTest() {
		super(ApiPath.CORPORATION, ApiPage.ASSET_LIST);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getResponse() throws ApiException {
		AbstractAssetListParser parser = AssetListParser.getInstance();
		AssetListResponse response = parser.getResponse(auth);
		assertNotNull("Should have returned a result.", response);
		assertDate(2008, 2, 3, 4, 43, 55, response.getCurrentTime());
		assertDate(2008, 2, 4, 3, 43, 55, response.getCachedUntil());
		Collection<EveAsset<EveAsset<?>>> assets = response.getAll();
		assertNotNull("Should have returned assets.", assets);
		assertEquals("There should have been 4 assets.", 4, assets.size());
		boolean assetFound = false;
		boolean subAssetFound = false;
		for (EveAsset asset : assets) {
			long itemID = asset.getItemID();
			if (100173218 == itemID) {
				assetFound = true;
				Collection<EveAsset> subAssets = asset.getAssets();
				assertNotNull("Should have returned assets.", subAssets);
				assertEquals("There should have been 5 sub assets.", 5, subAssets.size());
				for (EveAsset subAsset : subAssets) {
					if (105204820 == subAsset.getItemID()) {
						subAssetFound = true;
						Collection<EveAsset> subSubAssets = subAsset.getAssets();
						assertNotNull("Should have returned assets.", subSubAssets);
						assertEquals("There should have been 1 sub assets.", 1, subSubAssets.size());
					}
				}
			}
		}
		assertTrue("The asset with sub assets wasn't found", assetFound);
		assertTrue("The sub asset with a sub sub asset wasn't found", subAssetFound);
	}
}