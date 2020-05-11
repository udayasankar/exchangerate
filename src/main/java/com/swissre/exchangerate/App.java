package com.swissre.exchangerate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.swissre.exchangerate.services.ExchangeProcessingService;

@SpringBootApplication
public class App implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	ExchangeProcessingService exchangeProcessingService;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) {
		logger.info("EXECUTING : command line runner");

		String content = "START-OF-FILE\r\n" + "DATE=20181015\r\n" + "START-OF-FIELD-LIST\r\n" + "CURRENCY\r\n"
				+ "EXCHANGE_RATE\r\n" + "LAST_UPDATE\r\n" + "END-OF-FIELD-LIST\r\n" + "START-OF-EXCHANGE-RATES\r\n"
				+ "CHF|0.9832|17:12:59 10/14/2018|\r\n" + "GBP|0.7849|17:12:59 10/14/2018|\r\n"
				+ "EUR|0.8677|17:13:00 10/14/2018|\r\n" + "END-OF-EXCHANGE-RATES\r\n" + "END-OF-FILE";

		exchangeProcessingService.parseMessage(content);
	}
}
