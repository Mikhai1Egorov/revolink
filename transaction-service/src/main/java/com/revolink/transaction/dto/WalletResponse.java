package com.revolink.transaction.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletResponse {
    private UUID id;
    private double balance;
    private String currency;

    public WalletResponse(UUID id, double balance, String currency) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
    }
}