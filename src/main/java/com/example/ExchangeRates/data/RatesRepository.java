package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface RatesRepository extends CrudRepository<RateRecord, Long> {
    List<RateRecord> findByCurrency(Currency currency);

    RateRecord findFirstByCurrencySymbolIgnoreCaseOrderByDateDesc(String symbol);

    boolean existsRateRecordByCurrencyAndDate(Currency currency, Date date);

    @Query("SELECT r from RateRecord r JOIN FETCH r.currency " +
            "Where (r.currency, r.date) in " +
            "(Select currency, MAX(date) from RateRecord Group by currency)")
    List<RateRecord> getLastRates();

    List<RateRecord> findByCurrencyAndDateBetweenOrderByDate(Currency currency, Date dateStart, Date dateEnd);

}
