package com.example.ExchangeRates.shell;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.data.ExchangeRatesDataService;
import com.example.ExchangeRates.web.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ExchangeCommandsShell {

    private final ExchangeService exchangeService;
    private final ExchangeRatesDataService exchangeRatesDataService;

    @Autowired
    public ExchangeCommandsShell(
            ExchangeService exchangeService,
            ExchangeRatesDataService exchangeRatesDataService) {
        this.exchangeService = exchangeService;
        this.exchangeRatesDataService = exchangeRatesDataService;
    }

    @ShellMethod("Get available currencies")
    public String curr(){
        return exchangeRatesDataService.getCurrencies()
                .stream().map(Currency::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Download current exchange rates")
    public String load(){
        try {
            List<RateDTO> listRates = exchangeService.getRates();
            exchangeRatesDataService.save(listRates);
            return String.format("Get %d records", listRates.size());
        } catch (Exception e) {
            return "Error: "+e.getClass().getName();
        }
    }

    @ShellMethod("Get last rate for currency (need code)")
    public String rate(String currency){
        BigDecimal rate;
        try {
            rate = exchangeRatesDataService.getCurrencyRate(currency);
        } catch (Exception e) {
            return "<no information>";
        }
        return rate.toString();
    }

    @ShellMethod("Close program")
    public void close(){
        System.exit(0);
    }
}
