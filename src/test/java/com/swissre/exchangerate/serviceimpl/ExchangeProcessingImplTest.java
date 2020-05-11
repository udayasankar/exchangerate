package com.swissre.exchangerate.serviceimpl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.swissre.exchangerate.services.ExchangeProcessingService;
import com.swissre.exchangerate.utils.ExchangeRateConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeProcessingImplTest {

	@Autowired
	ExchangeProcessingService exchangeProcessingService;

	String message,startMessage,endMessage;

	@Before
	public void init() {

		message = "START-OF-FILE\r\n" + "DATE=20181015\r\n" + "START-OF-FIELD-LIST\r\n" + "CURRENCY\r\n"
				+ "EXCHANGE_RATE\r\n" + "LAST_UPDATE\r\n" + "END-OF-FIELD-LIST\r\n" + "START-OF-EXCHANGE-RATES\r\n"
				+ "CHF|0.9832|17:12:59 10/14/2018|\r\n" + "GBP|0.7849|17:12:59 10/14/2018|\r\n"
				+ "EUR|0.8677|17:13:00 10/14/2018|\r\n" + "END-OF-EXCHANGE-RATES\r\n" + "END-OF-FILE";
		
		startMessage = "START-OF-FILE\r\n" + "DATE=20181015\r\n" + "START-OF-FIELD-LIST\r\n" + "CURRENCY\r\n"
				+ "EXCHANGE_RATE\r\n" + "LAST_UPDATE\r\n" + "END-OF-FIELD-LIST\r\n" 
				+ "CHF|0.9832|17:12:59 10/14/2018|\r\n" + "GBP|0.7849|17:12:59 10/14/2018|\r\n"
				+ "EUR|0.8677|17:13:00 10/14/2018|\r\n" + "END-OF-EXCHANGE-RATES\r\n" + "END-OF-FILE";
		
		endMessage = "START-OF-FILE\r\n" + "DATE=20181015\r\n" + "START-OF-FIELD-LIST\r\n" + "CURRENCY\r\n"
				+ "EXCHANGE_RATE\r\n" + "LAST_UPDATE\r\n" + "END-OF-FIELD-LIST\r\n" + "START-OF-EXCHANGE-RATES\r\n"
				+ "CHF|0.9832|17:12:59 10/14/2018|\r\n" + "GBP|0.7849|17:12:59 10/14/2018|\r\n"
				+ "EUR|0.8677|17:13:00 10/14/2018|\r\n" + "END-OF-FILE";
	}

	@Test
	public void testDuplicateFileName() throws Exception {

		String fileName = "exchangerate.txt";
		assertEquals(exchangeProcessingService.checkDuplicate(fileName), true);
	}

	@Test
	public void testNotDuplicateFileName() throws Exception {

		String fileName = "exchangeratePrev.txt";
		assertEquals(exchangeProcessingService.checkDuplicate(fileName), false);
	}

	@Test
	public void testEmptyMessage() throws Exception {
		String emptyMessage = "";
		assertEquals(exchangeProcessingService.parseMessage(emptyMessage), ExchangeRateConstants.FILE_PROCESS_FAILURE);
	}
	
	@Test
	public void testStartExchange() throws Exception {
		assertEquals(exchangeProcessingService.parseMessage(startMessage), ExchangeRateConstants.NO_START);
	}
	
	@Test
	public void testEndExchange() throws Exception {

		String emptyMessage = "";
		assertEquals(exchangeProcessingService.parseMessage(endMessage), ExchangeRateConstants.NO_ENDOF);
	}
	
	@Test
	public void testExchangeValues() throws Exception {

		String messageExchange = "START-OF-FILE\r\n" + "DATE=20181015\r\n" + "START-OF-FIELD-LIST\r\n" + "CURRENCY\r\n"
				+ "EXCHANGE_RATE\r\n" + "LAST_UPDATE\r\n" + "END-OF-FIELD-LIST\r\n" + "START-OF-EXCHANGE-RATES\r\n"
				+ "END-OF-EXCHANGE-RATES\r\n" + "END-OF-FILE";
		assertEquals(exchangeProcessingService.parseMessage(messageExchange), ExchangeRateConstants.NO_EXCHANE_VALUES);
	}
	
	@Test
	public void testListExchangeRates() throws Exception {

		String strMessage = "CHF|0.9832|17:12:59 10/14/2018|\r\n" + "GBP|0.7849|17:12:59 10/14/2018|\r\n"
				+ "EUR|0.8677|17:13:00 10/14/2018|\r\n";
		assertEquals(exchangeProcessingService.listExchangeRates(strMessage), ExchangeRateConstants.FILE_PROCESS_SUCESS);
	}
	

	@Test
	public void testparseMessage() throws Exception {
		assertEquals(exchangeProcessingService.parseMessage(message), ExchangeRateConstants.FILE_PROCESS_SUCESS);
	}

	@Test
	public void testcheckPercentMoreEmpty() throws Exception {
		String emptyMessage = "";
		assertEquals(exchangeProcessingService.checkPercentMore(emptyMessage), ExchangeRateConstants.EMPTY_MESSAGE);
	}

	@Test
	public void testcheckPercentMore() throws Exception {
		assertEquals(exchangeProcessingService.checkPercentMore(message), ExchangeRateConstants.MESSAGE_PROCESSED);
	}

	@Test
	public void testmonthlyAvgRateEmpty() throws Exception {
		String emptyMessage = "";
		assertEquals(exchangeProcessingService.monthlyAvgRate(emptyMessage), ExchangeRateConstants.EMPTY_MESSAGE);
	}

	@Test
	public void testmonthlyAvgRate() throws Exception {
		assertEquals(exchangeProcessingService.monthlyAvgRate(message), ExchangeRateConstants.MESSAGE_PROCESSED);
	}

	@Test
	public void testyearlyAvgRateEmpty() throws Exception {
		String emptyMessage = "";
		assertEquals(exchangeProcessingService.yearlyAvgRate(emptyMessage), ExchangeRateConstants.EMPTY_MESSAGE);
	}

	@Test
	public void testyearlyAvgRate() throws Exception {
		assertEquals(exchangeProcessingService.yearlyAvgRate(message), ExchangeRateConstants.MESSAGE_PROCESSED);
	}

}
