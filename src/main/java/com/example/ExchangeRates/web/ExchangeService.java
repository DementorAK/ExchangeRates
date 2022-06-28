package com.example.ExchangeRates.web;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeService {
    List<RateDTO> getRates();

    List<RateDTO> getRatesCurrencyInRange(Currency currency, LocalDate start, LocalDate end);

}
