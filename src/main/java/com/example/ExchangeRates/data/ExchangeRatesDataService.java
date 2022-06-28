package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.DTO.RateRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface ExchangeRatesDataService {

    void save(List<RateDTO> rates);

    List<Currency> getCurrencies();

    BigDecimal getCurrencyRate(String symbolCurrency);

    List<RateDTO> getLastRates();

    List<RateRecord> getRatesInRange(Currency currency, Date dateStart, Date dateEnd);

}
