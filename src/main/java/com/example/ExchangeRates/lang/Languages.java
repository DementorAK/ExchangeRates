package com.example.ExchangeRates.lang;

import com.example.ExchangeRates.ApplicationContextHolder;
import java.util.Locale;

public enum Languages {
    EN(Locale.US),
    UA(new Locale("uk", "UA")),
    RU(new Locale("ru", "RU")),
    DE(Locale.GERMAN),
    ES(new Locale("es", "ES"));

    public final Locale locale;

    Languages(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        I18n i18n = ApplicationContextHolder.getApplicationContext().getBean(I18n.class);
        return i18n.getMessage("lang."+this.name());
    }
}
