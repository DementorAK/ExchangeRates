package com.example.ExchangeRates.lang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class I18nTest {

    @Autowired
    private I18n i18n;

    @Test
    void getMessage() {

        String result = "";

        LocaleContextHolder.setLocale(Locale.US);
        result = i18n.getMessage("title");
        assertEquals("Exchange rates from bank.gov.ua", result);

        LocaleContextHolder.setLocale(new Locale("uk", "UA"));
        result = i18n.getMessage("title");
        assertEquals("Курси валют з bank.gov.ua", result);

    }
}