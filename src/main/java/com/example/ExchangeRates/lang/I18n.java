package com.example.ExchangeRates.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class I18n {

    @Autowired
    private MessageSource messageSource;

    public I18n(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, "Default Message", locale);
    }

    public String getMessage(String key, Locale locale){
        return messageSource.getMessage(key, null, "Default Message", locale);
    }

    public ResourceBundle getBundle(){
        Locale locale = LocaleContextHolder.getLocale();
        return ResourceBundle.getBundle("messages", locale);
    }

}
