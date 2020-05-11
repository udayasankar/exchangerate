package com.swissre.exchangerate.services;

public interface ExchangeProcessingService {

	public boolean checkDuplicate(String fileName);
	
	public String listExchangeRates(String str);

	public String parseMessage(String message);

	public String checkPercentMore(String exchangeMessage);

	public String monthlyAvgRate(String exchangeMessage);

	public String yearlyAvgRate(String exchangeMessage);

}
