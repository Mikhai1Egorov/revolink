package com.revolink.transaction.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCompletedEvent implements Serializable {
    private UUID fromWalletId;
    private UUID toWalletId;
    private double amount;
    private String currency;
}