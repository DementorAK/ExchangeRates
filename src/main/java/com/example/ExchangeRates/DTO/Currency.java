package com.example.ExchangeRates.DTO;

public class Currency {
    private final Short code;
    private final String symbol;
    private final String description;

    public Currency(Short code, String symbol, String description) {
        this.code = code;
        this.symbol = symbol;
        this.description = description;
    }

    public Short getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return symbol + " " + description;
    }
}
