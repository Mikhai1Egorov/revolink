package com.revolink.exchange.service.impl;

import com.revolink.exchange.config.ExchangeRatesConfig;
import com.revolink.exchange.service.interf.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExchangeServiceImplTest {

    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        ExchangeRatesConfig config = new ExchangeRatesConfig(); // загружает хардкод
        exchangeService = new ExchangeServiceImpl(config);
    }

    @Test
    void shouldReturnCorrectExchangeRate() {
        double rate = exchangeService.getExchangeRate("EUR", "USD");
        assertEquals(1.12, rate, 0.0001);
    }

    @Test
    void shouldReturnReverseRate() {
        double rate = exchangeService.getExchangeRate("USD", "EUR");
        assertEquals(0.89, rate, 0.0001);
    }

    @Test
    void shouldThrowExceptionForUnknownPair() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                exchangeService.getExchangeRate("EUR", "JPY")
        );
        assertEquals("Exchange rate not found for EUR to JPY", exception.getMessage());
    }
}