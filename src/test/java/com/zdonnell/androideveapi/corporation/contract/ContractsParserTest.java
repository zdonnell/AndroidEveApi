package com.zdonnell.androideveapi.corporation.contract;

import static com.zdonnell.androideveapi.utils.Assert.assertDate;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.zdonnell.androideveapi.core.ApiPage;
import com.zdonnell.androideveapi.core.ApiPath;
import com.zdonnell.androideveapi.corporation.contract.ContractsParser;
import com.zdonnell.androideveapi.exception.ApiException;
import com.zdonnell.androideveapi.shared.contract.ContractAvailability;
import com.zdonnell.androideveapi.shared.contract.ContractStatus;
import com.zdonnell.androideveapi.shared.contract.ContractType;
import com.zdonnell.androideveapi.shared.contract.ContractsResponse;
import com.zdonnell.androideveapi.shared.contract.EveContract;
import com.zdonnell.androideveapi.utils.FullAuthParserTest;

public class ContractsParserTest extends FullAuthParserTest {
	public ContractsParserTest() {
		super(ApiPath.CORPORATION, ApiPage.CONTRACTS);
	}

	@Test
	public void getResponse() throws ApiException {
		ContractsParser parser = ContractsParser.getInstance();
		ContractsResponse response = parser.getResponse(auth); 
		assertNotNull(response);
		Collection<EveContract> contracts = response.getAll();
		assertNotNull(contracts);
		assertEquals(6, contracts.size());
		boolean found = false;
		for (EveContract contract : contracts) {
			if(contract.getContractID()==62276415L) {
				found = true;
				assertEquals(1683690353L, contract.getIssuerID());
				assertEquals(1020386980L, contract.getIssuerCorpID());
				assertEquals(1020386980L, contract.getAssigneeID());
				assertEquals(0L, contract.getAcceptorID());
				assertEquals(60010600, contract.getStartStationID());
				assertEquals(60010600, contract.getEndStationID());
				assertEquals(ContractType.ITEMEXCHANGE, contract.getType());
				assertEquals(ContractStatus.OUTSTANDING, contract.getStatus());
				assertEquals("From corp to corp (Item exchange)", contract.getTitle());
				assertTrue(contract.isForCorp());
				assertEquals(ContractAvailability.PRIVATE, contract.getAvailability());
				assertDate("2012-12-14 21:49:13", contract.getDateIssued());
				assertDate("2012-12-28 21:49:13", contract.getDateExpired());
				assertNull(contract.getDateAccepted());
				assertNull(contract.getDateCompleted());
				assertEquals(2, contract.getNumDays());
				assertEquals(1000000.00, contract.getPrice(), 0.01);
				assertEquals(0.0, contract.getReward(), 0.01);
				assertEquals(0.0, contract.getCollateral(), 0.01);
				assertEquals(0.0, contract.getBuyout(),0.01);
				assertEquals(0.1, contract.getVolume(),0.01);
			}
		}
		assertTrue("test contract wasn't found.", found);
	}
}