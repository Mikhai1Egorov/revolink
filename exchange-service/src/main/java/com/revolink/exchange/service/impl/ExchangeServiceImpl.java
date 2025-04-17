package com.revolink.exchange.service.impl;

import com.revolink.exchange.config.ExchangeRatesConfig;
import com.revolink.exchange.service.interf.ExchangeService;

import java.util.Map;

public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRatesConfig exchangeRatesConfig;

    public ExchangeServiceImpl(ExchangeRatesConfig exchangeRatesConfig) {
        this.exchangeRatesConfig = exchangeRatesConfig;
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        String key = fromCurrency + "/" + toCurrency;
        Map<String, Double> rates = exchangeRatesConfig.getRates();

        if (rates.containsKey(key)) {
            return rates.get(key);
        } else {
            throw new IllegalArgumentException("Exchange rate not found for " + fromCurrency + " to " + toCurrency);
        }
    }
}