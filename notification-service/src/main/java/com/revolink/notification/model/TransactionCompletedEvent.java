package com.revolink.notification.model;

import lombok.Data;
import java.util.UUID;

@Data
public class TransactionCompletedEvent {
    private UUID fromWalletId;
    private UUID toWalletId;
    private double amount;
    private String currency;
}