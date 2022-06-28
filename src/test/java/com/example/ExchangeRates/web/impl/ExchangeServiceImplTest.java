package com.example.ExchangeRates.web.impl;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.web.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeServiceImplTest {

    @Autowired
    private ExchangeService exchangeService;

    @Test
    void getRates() {
        List<RateDTO> rates = exchangeService.getRates();
        assertNotNull(rates);
        assertNotEquals(0, rates.size());
        for (RateDTO rate: rates) {
            System.out.println(rate);
        }
    }

    @Test
    void getRatesCurrencyInRange() {
        List<RateDTO> rates = exchangeService.getRatesCurrencyInRange(
                new Currency((short) 978, "EUR", "Евро"),
                LocalDate.now().minusDays(10),
                LocalDate.now()
        );
        assertNotNull(rates);
        assertNotEquals(0, rates.size());
        for (RateDTO rate: rates) {
            System.out.println(rate);
        }
    }
}