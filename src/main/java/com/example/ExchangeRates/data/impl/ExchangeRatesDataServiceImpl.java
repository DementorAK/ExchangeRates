package com.example.ExchangeRates.data.impl;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.DTO.RateRecord;
import com.example.ExchangeRates.data.CurrencyRepository;
import com.example.ExchangeRates.data.ExchangeRatesDataService;
import com.example.ExchangeRates.data.RatesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExchangeRatesDataServiceImpl implements ExchangeRatesDataService {

    private final RatesRepository ratesRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeRatesDataServiceImpl(RatesRepository ratesRepository, CurrencyRepository currencyRepository) {
        this.ratesRepository = ratesRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    @Transactional
    public void save(List<RateDTO> rates) {
        for (RateDTO rate: rates) {
            Currency currency = new Currency(rate.getR030(), rate.getCC(), rate.getTxt());
            if (!currencyRepository.existsById(currency.getCode()))
                currencyRepository.save(currency);
            if (!ratesRepository.existsRateRecordByCurrencyAndDate(currency, rate.getExchangeDate()))
                ratesRepository.save(new RateRecord(currency, rate.getExchangeDate(), rate.getRate()));
        }
    }

    @Override
    public List<Currency> getCurrencies() {
        return (List<Currency>) currencyRepository.findAll();
    }

    @Override
    public BigDecimal getCurrencyRate(String symbolCurrency) {
        RateRecord rateRecord = ratesRepository.findFirstByCurrencySymbolIgnoreCaseOrderByDateDesc(symbolCurrency);
        return rateRecord.getRate();
    }

}
