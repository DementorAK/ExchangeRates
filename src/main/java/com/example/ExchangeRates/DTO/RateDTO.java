package com.example.ExchangeRates.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RateDTO {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMMM.yyyy");

    private String txt;
    private BigDecimal rate;
    private String cc;
    private Short r030;
    private String exchangedate;

    public String getTxt() {
        return txt;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getCC() {
        return cc;
    }

    public Short getR030() {
        return r030;
    }

    public Date getExchangeDate() {
        if (exchangedate==null || exchangedate.isEmpty())
            return new Date(System.currentTimeMillis());
        try {
            return new Date(dateFormat.parse(exchangedate).getTime());
        } catch (ParseException e) {
            return new Date(System.currentTimeMillis());
        }
    }

    @Override
    public String toString() {
        return cc + " " + txt + ", rate=" + rate;
    }
}
