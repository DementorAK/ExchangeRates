package com.example.ExchangeRates;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateRecord;
import com.example.ExchangeRates.data.CurrencyRepository;
import com.example.ExchangeRates.data.RatesRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ExchangeRatesApplicationTests {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private RatesRepository ratesRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCountCurrencies(){
		System.out.println("Currencies: "+currencyRepository.count());
		assertNotEquals(0, currencyRepository.count());
	}

	@Test
	public void testGetRateByCurrency(){
		Optional<Currency> euro = currencyRepository.findById((short) 978);
		assertFalse(euro.isEmpty());

		List<RateRecord> records = ratesRepository.findByCurrency(euro.get());
		assertFalse(records.isEmpty());
		System.out.println(records);
	}

}
