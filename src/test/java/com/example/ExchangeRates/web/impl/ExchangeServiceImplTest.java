package com.example.ExchangeRates.web.impl;

import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.web.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner
                .SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner
                .SPRING_SHELL_SCRIPT_ENABLED + "=false"})
class ExchangeServiceImplTest {

    @Autowired
    private ExchangeService exchangeService;

    @Test
    void getRates() {
        List<RateDTO> rates = exchangeService.getRates();
        assertNotNull(rates);
        assertNotEquals(0, rates.size());
        for (RateDTO rate: rates) {
            System.out.println(rate);
        }
    }
}