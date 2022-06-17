package com.example.ExchangeRates.data;

import com.example.ExchangeRates.DTO.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Short> {

    Currency findBySymbol(String symbol);

}
