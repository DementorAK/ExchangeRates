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
import java.sql.Date;
import java.util.ArrayList;
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
            Currency currency = new Currency(rate.getR030(), rate.getCc(), rate.getTxt());
            if (!currencyRepository.existsById(currency.getCode()))
                currencyRepository.save(currency);
            if (!ratesRepository.existsRateRecordByCurrencyAndDate(currency, rate.getExchangedate()))
                ratesRepository.save(new RateRecord(currency, rate.getExchangedate(), rate.getRate()));
        }
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyRepository.findByOrderBySymbolAsc();
    }

    @Override
    public BigDecimal getCurrencyRate(String symbolCurrency) {
        RateRecord rateRecord = ratesRepository.findFirstByCurrencySymbolIgnoreCaseOrderByDateDesc(symbolCurrency);
        return rateRecord.getRate();
    }

    @Override
    public List<RateDTO> getLastRates() {

        List<RateRecord> records = ratesRepository.getLastRates();

        List<RateDTO> result = new ArrayList<>();
        for (RateRecord record: records) {
            result.add(new RateDTO(
                    record.getCurrency().getDescription(),
                    record.getCurrency().getSymbol(),
                    record.getCurrency().getCode(),
                    record.getRate(),
                    record.getDate().toString()));
        }

        return result;
    }

    @Override
    public List<RateRecord> getRatesInRange(Currency currency, Date dateStart, Date dateEnd) {
        return ratesRepository.findByCurrencyAndDateBetweenOrderByDate(currency, dateStart, dateEnd);
    }

}
