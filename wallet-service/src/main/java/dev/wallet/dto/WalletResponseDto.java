package dev.wallet.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record WalletResponseDto(
        UUID id,
        String currency,
        BigDecimal balance,
        Instant createdAt
) {}