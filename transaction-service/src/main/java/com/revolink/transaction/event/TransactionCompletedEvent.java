package com.revolink.transaction.event;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class TransactionCompletedEvent implements Serializable {
    private UUID fromWalletId;
    private UUID toWalletId;
    private double amount;
    private String currency;

    public TransactionCompletedEvent() {
    }

    public TransactionCompletedEvent(UUID fromWalletId, UUID toWalletId, double amount, String currency) {
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.currency = currency;
    }
}