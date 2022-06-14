package com.example.ExchangeRates.data.impl;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.data.ExchangeRatesDataService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRatesDataServiceImpl implements ExchangeRatesDataService {

    private final JdbcTemplate jdbcTemplate;

    public ExchangeRatesDataServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(List<RateDTO> rates) {

        Map<Short, Currency> currencyMap = new HashMap<>(rates.size());

        for (RateDTO rate: rates) {
            if (!currencyMap.containsKey(rate.getR030())) {
                currencyMap.put(
                        rate.getR030(),
                        new Currency(rate.getR030(), rate.getCC(), rate.getTxt()));
            }
        }

        List<Currency> currencies = new ArrayList<>(currencyMap.values());

        int[][] resultsCurrencies = jdbcTemplate.batchUpdate(
                "Merge into Currencies key (code, symbol, description) values (?,?,?)",
                currencies,
                20,
                (ps, argument) -> {
                    ps.setShort(1, argument.getCode());
                    ps.setString(2, argument.getSymbol());
                    ps.setString(3, argument.getDescription());
                });

        int[][] resultsRates = jdbcTemplate.batchUpdate(
                "Merge into Rates key (currency, date, rate) values (?,?,?)",
                rates,
                20,
                (ps, argument) -> {
                    ps.setShort(1, argument.getR030());
                    ps.setDate(2, argument.getExchangeDate());
                    ps.setBigDecimal(3, argument.getRate());
                });

    }

    @Override
    public List<Currency> getCurrencies() {
        return jdbcTemplate.query(
                "Select * from Currencies order by symbol",
                (rs, rowNum) -> new Currency(
                        rs.getShort("code"),
                        rs.getString("symbol"),
                        rs.getString("description")
                ));
    }

    @Override
    public Map<Currency, BigDecimal> getRates() {
        return null;
    }

    @Override
    public BigDecimal getCurrencyRate(String currency) {
        return jdbcTemplate.queryForObject(
                "Select TOP 1 rate FROM Rates " +
                        "INNER JOIN Currencies " +
                        "   ON Rates.currency=Currencies.code AND Currencies.symbol=? " +
                        "ORDER BY date DESC",
                (rs, rowNum) -> rs.getBigDecimal("rate"),
                currency);
    }
}
