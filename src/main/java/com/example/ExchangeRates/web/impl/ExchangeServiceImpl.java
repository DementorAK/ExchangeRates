package com.example.ExchangeRates.web.impl;

import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.web.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;


@Service
public class ExchangeServiceImpl implements ExchangeService {

    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    public ExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RateDTO> getRates() {
        logger.debug("getRates() started");
        ResponseEntity<List<RateDTO>> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        logger.info(response.toString());
        logger.debug("getRates() finished");
        return response.getBody();
    }
}
