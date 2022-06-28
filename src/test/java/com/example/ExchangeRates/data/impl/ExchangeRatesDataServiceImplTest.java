package com.example.ExchangeRates.data.impl;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.DTO.RateRecord;
import com.example.ExchangeRates.data.ExchangeRatesDataService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:tesdb"})
class ExchangeRatesDataServiceImplTest {

    static List<RateDTO> testRates;

    @BeforeAll
    static void beforeAll() {
        testRates = new ArrayList<>();

        testRates.add(new RateDTO("Доллар", "USD", (short) 840, BigDecimal.valueOf(25), "01.01.2022"));
        testRates.add(new RateDTO("Доллар", "USD", (short) 840, BigDecimal.valueOf(26), "01.02.2022"));
        testRates.add(new RateDTO("Доллар", "USD", (short) 840, BigDecimal.valueOf(27), "01.03.2022"));
        testRates.add(new RateDTO("Доллар", "USD", (short) 840, BigDecimal.valueOf(28), "01.04.2022"));

        testRates.add(new RateDTO("Евро", "EUR", (short) 978, BigDecimal.valueOf(29), "01.01.2022"));
        testRates.add(new RateDTO("Евро", "EUR", (short) 978, BigDecimal.valueOf(30), "01.02.2022"));
        testRates.add(new RateDTO("Евро", "EUR", (short) 978, BigDecimal.valueOf(31), "01.03.2022"));
        testRates.add(new RateDTO("Евро", "EUR", (short) 978, BigDecimal.valueOf(32), "01.04.2022"));

    }

    @Autowired
    private ExchangeRatesDataService exchangeRatesDataService;

    @Test
    void save() {
        exchangeRatesDataService.save(testRates);
    }

    @Test
    void getCurrencies() {
        save();
        List<Currency> currencies = exchangeRatesDataService.getCurrencies();
        assertNotNull(currencies);
        assertEquals(2, currencies.size());
    }

    @Test
    void getCurrencyRate() {
        save();
        BigDecimal rate = exchangeRatesDataService.getCurrencyRate("USD");
        assertNotNull(rate);
        assertEquals(0, BigDecimal.valueOf(28).compareTo(rate));
    }

    @Test
    void getLastRates() {
        save();
        List<RateDTO> rates = exchangeRatesDataService.getLastRates();
        assertNotNull(rates);
        assertEquals(2, rates.size());
    }

    @Test
    void getRatesInRange() {

        save();

        Currency euro = new Currency((short) 978, "EUR", "Евро");

        List<RateRecord> records = exchangeRatesDataService.getRatesInRange(
                euro,
                Date.valueOf("2020-01-01"),
                Date.valueOf("2020-12-31"));
        assertNotNull(records);
        assertEquals(0, records.size());

        records = exchangeRatesDataService.getRatesInRange(
                euro,
                Date.valueOf("2022-01-01"),
                Date.valueOf("2022-12-31"));
        assertNotNull(records);
        assertEquals(4, records.size());

    }
}