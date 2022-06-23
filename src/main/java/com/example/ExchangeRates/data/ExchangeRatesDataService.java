package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRatesDataService {

    void save(List<RateDTO> rates);

    List<Currency> getCurrencies();

    BigDecimal getCurrencyRate(String symbolCurrency);

}
