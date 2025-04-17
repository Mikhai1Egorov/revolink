package com.revolink.transaction.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private UUID fromWalletId;
    private UUID toWalletId;
    private double amount;
    private String currency;
}