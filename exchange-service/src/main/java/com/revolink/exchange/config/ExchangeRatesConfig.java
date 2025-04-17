package com.revolink.exchange.config;

import java.util.Map;
import java.util.HashMap;

public class ExchangeRatesConfig {
    private final Map<String, Double> exchangeRates;

    public ExchangeRatesConfig() {
        this.exchangeRates = new HashMap<>();
        loadRates();
    }

    private void loadRates() {
        exchangeRates.put("EUR/USD", 1.12);
        exchangeRates.put("USD/EUR", 0.89);
        exchangeRates.put("EUR/GBP", 0.85);
        exchangeRates.put("GBP/EUR", 1.18);
    }


    public Map<String, Double> getRates() {
        return exchangeRates;
    }
}