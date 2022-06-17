package com.example.ExchangeRates.DTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Rates")
public class RateRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency")
    private Currency currency;

    @Column
    private Date date;

    @Column
    private BigDecimal rate;

    public RateRecord(long id, Currency currency, Date date, BigDecimal rate) {
        this.id = id;
        this.currency = currency;
        this.date = date;
        this.rate = rate;
    }

    public RateRecord(Currency currency, Date date, BigDecimal rate) {
        this.currency = currency;
        this.date = date;
        this.rate = rate;
        this.id = 0L;
    }

    public RateRecord() {

    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
