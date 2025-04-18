package com.revolink.exchange.starter;

import com.revolink.exchange.config.ExchangeRatesConfig;
import com.revolink.exchange.controller.ExchangeController;
import com.revolink.exchange.service.impl.ExchangeServiceImpl;
import com.revolink.exchange.service.interf.ExchangeService;

public class Starter {
    public static void start() {
        ExchangeRatesConfig exchangeRatesConfig = new ExchangeRatesConfig();
        ExchangeService exchangeService = new ExchangeServiceImpl(exchangeRatesConfig);
        ExchangeController exchangeController = new ExchangeController(exchangeService);

        exchangeController.init();

        System.out.println("Exchange Service is running on port 8083");
    }
}