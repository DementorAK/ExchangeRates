package com.example.ExchangeRates.web.impl;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.web.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ExchangeServiceImpl implements ExchangeService {

    private static final String URL_now = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final String URL_range = "https://bank.gov.ua/NBU_Exchange/exchange_site?json&sort=exchangedate";
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    public ExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RateDTO> getRates() {
        logger.debug("getRates() started");
        ResponseEntity<List<RateDTO>> response = restTemplate.exchange(
                URL_now,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        logger.info(response.toString());
        logger.debug("getRates() finished");
        return response.getBody();
    }

    @Override
    public List<RateDTO> getRatesCurrencyInRange(Currency currency, LocalDate start, LocalDate end) {
        logger.debug("getRatesCurrencyInRange() started");
        String currentURL = URL_range +
                "&valcode="+currency.getSymbol()+
                "&start="+start.format(DateTimeFormatter.BASIC_ISO_DATE)+
                "&end="+end.format(DateTimeFormatter.BASIC_ISO_DATE);
        logger.debug(currentURL);
        ResponseEntity<List<RateDTO>> response = restTemplate.exchange(
                currentURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        logger.info(response.toString());
        logger.debug("getRatesCurrencyInRange() finished");
        return response.getBody();
    }
}
