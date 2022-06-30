package com.example.ExchangeRates.lang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LanguagesTest {

    @Test
    void testToString() {

        LocaleContextHolder.setLocale(Languages.EN.locale);
        assertEquals("Deutsch", Languages.DE.toString());

        LocaleContextHolder.setLocale(Languages.UA.locale);
        assertEquals("Німецька", Languages.DE.toString());

    }
}