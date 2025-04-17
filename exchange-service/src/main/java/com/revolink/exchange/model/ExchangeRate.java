package com.revolink.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRate {
    private String fromCurrency;
    private String toCurrency;
    private double rate;
}