package com.example.ExchangeRates.web;

import com.example.ExchangeRates.DTO.RateDTO;

import java.util.List;

public interface ExchangeService {
    List<RateDTO> getRates();
}
