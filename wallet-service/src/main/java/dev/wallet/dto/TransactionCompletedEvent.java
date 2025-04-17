package dev.wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionCompletedEvent(
    UUID fromWalletId,
    UUID toWalletId,
    BigDecimal amount,
    String currency
) {}