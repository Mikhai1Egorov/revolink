package com.revolink.exchange;

import com.revolink.exchange.config.ExchangeRatesConfig;
import com.revolink.exchange.controller.ExchangeController;
import com.revolink.exchange.service.impl.ExchangeServiceImpl;
import com.revolink.exchange.service.interf.ExchangeService;

import static spark.Spark.*;

public class ExchangeServiceApplication {

    public static void main(String[] args) {
        port(8080);

        ExchangeRatesConfig exchangeRatesConfig = new ExchangeRatesConfig();
        ExchangeService exchangeService = new ExchangeServiceImpl(exchangeRatesConfig);
        ExchangeController exchangeController = new ExchangeController(exchangeService);

        exchangeController.init();

        System.out.println("Exchange Service is running on port 8080");
    }
}