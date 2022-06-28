package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Short>, JpaRepository<Currency, Short> {

    Currency findBySymbol(String symbol);

    List<Currency> findByOrderBySymbolAsc();
}
