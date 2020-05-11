package com.swissre.exchangerate.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.swissre.exchangerate.services.ExchangeProcessingService;
import com.swissre.exchangerate.utils.ExchangeRateConstants;

@Component
public class ExchangeProcessingImpl implements ExchangeProcessingService {
	private static Logger logger = LoggerFactory.getLogger(ExchangeProcessingImpl.class);

	public static String prevFileName = "exchangerate.txt";

	public boolean checkDuplicate(String fileName) {
		if (prevFileName.equalsIgnoreCase(fileName))
			return true;
		return false;

	}

	public String parseMessage(String message) {
		if (message.equals(""))
			return ExchangeRateConstants.FILE_PROCESS_FAILURE;
		try {
			StringBuffer sbMessage = new StringBuffer(message);
			if (sbMessage.lastIndexOf(ExchangeRateConstants.STARTSEARCH) < 0)
					return ExchangeRateConstants.NO_START;
			if(sbMessage.indexOf(ExchangeRateConstants.ENDSEARCH) < 0)
				return ExchangeRateConstants.NO_ENDOF;
			String str = sbMessage.substring(
					sbMessage.lastIndexOf(ExchangeRateConstants.STARTSEARCH)
							+ ExchangeRateConstants.STARTSEARCH.length() + 1,
					sbMessage.indexOf(ExchangeRateConstants.ENDSEARCH)).trim();
			if(str.length()==0) return ExchangeRateConstants.NO_EXCHANE_VALUES;
			listExchangeRates(str);
		} catch (Exception ex) {
			logger.error("Error in parseMessage function {}", ex);
			return ExchangeRateConstants.FILE_PROCESS_FAILURE;
		}
		return ExchangeRateConstants.FILE_PROCESS_SUCESS;

	}
	
	public String listExchangeRates(String str)
	{
		String[] lines = str.split("\\r?\\n");
		System.out.println("Exchange Rates");
		for (String line : lines) {
			String[] pipeSplit = line.split("\\|");
			float priceValue = Float.parseFloat(pipeSplit[1]);
			float convtValue = priceValue * 100 * ExchangeRateConstants.USD;
			System.out.println(pipeSplit[0] + " : " + pipeSplit[1] + " : " + convtValue);
		}
		return ExchangeRateConstants.FILE_PROCESS_SUCESS;
	}

	public String checkPercentMore(String exchangeMessage) {
		if (exchangeMessage.equals(""))
			return ExchangeRateConstants.EMPTY_MESSAGE;
		return ExchangeRateConstants.MESSAGE_PROCESSED;
	}

	public String monthlyAvgRate(String exchangeMessage) {
		if (exchangeMessage.equals(""))
			return ExchangeRateConstants.EMPTY_MESSAGE;
		return ExchangeRateConstants.MESSAGE_PROCESSED;
	}

	public String yearlyAvgRate(String exchangeMessage) {
		if (exchangeMessage.equals(""))
			return ExchangeRateConstants.EMPTY_MESSAGE;
		return ExchangeRateConstants.MESSAGE_PROCESSED;
	}

}
