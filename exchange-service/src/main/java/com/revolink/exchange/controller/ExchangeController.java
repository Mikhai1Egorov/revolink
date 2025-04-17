package com.revolink.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolink.exchange.model.ExchangeRate;
import com.revolink.exchange.service.interf.ExchangeService;

import static spark.Spark.get;
import static spark.Spark.exception;

public class ExchangeController {
    private final ExchangeService exchangeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    public void init() {
        exception(IllegalArgumentException.class, (ex, req, res) -> {
            res.status(404);
            res.type("application/json");
            res.body("{\"error\": \"" + ex.getMessage() + "\"}");
        });

        get("/api/exchange/:fromCurrency/:toCurrency", (req, res) -> {
            String fromCurrency = req.params(":fromCurrency");
            String toCurrency = req.params(":toCurrency");

            double rate = exchangeService.getExchangeRate(fromCurrency, toCurrency);

            ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, toCurrency, rate);
            res.type("application/json");
            return objectMapper.writeValueAsString(exchangeRate);
        });
    }
}