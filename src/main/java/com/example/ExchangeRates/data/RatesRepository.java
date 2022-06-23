package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateRecord;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface RatesRepository extends CrudRepository<RateRecord, Long> {
    List<RateRecord> findByCurrency(Currency currency);

    RateRecord findFirstByCurrencySymbolIgnoreCaseOrderByDateDesc(String symbol);
    List<RateRecord> findByCurrencySymbolIgnoreCaseOrderByDateDesc(String symbol);

    boolean existsRateRecordByCurrencyAndDate(Currency currency, Date date);
}
