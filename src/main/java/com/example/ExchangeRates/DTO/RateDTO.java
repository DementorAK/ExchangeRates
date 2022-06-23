package com.example.ExchangeRates.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RateDTO {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

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

    public RateDTO() {
    }

    public RateDTO(String txt, String cc, Short r030, BigDecimal rate, String exchangedate) {
        this.txt = txt;
        this.rate = rate;
        this.cc = cc;
        this.r030 = r030;
        this.exchangedate = exchangedate;
    }

    @Override
    public String toString() {
        return cc + " " + txt + ", rate=" + rate;
    }
}
