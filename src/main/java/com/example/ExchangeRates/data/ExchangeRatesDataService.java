package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExchangeRatesDataService {

    void save(List<RateDTO> rates);

    List<Currency> getCurrencies();

    Map<Currency, BigDecimal> getRates();

    BigDecimal getCurrencyRate(String currency);

}
