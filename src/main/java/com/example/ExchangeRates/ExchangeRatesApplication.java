package com.example.ExchangeRates;

import com.example.ExchangeRates.data.ExchangeRatesDataService;
import com.example.ExchangeRates.data.impl.ExchangeRatesDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ExchangeRatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRatesApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void createExchangeTables(){
		jdbcTemplate.execute(
				"Create table if not exists Currencies " +
						"(code SMALLINT PRIMARY KEY, symbol CHAR(3), description VARCHAR) ");

		jdbcTemplate.execute(
					"Create table if not exists Rates " +
						"(currency SMALLINT, date TIMESTAMP, rate DECFLOAT)");
	}
}
