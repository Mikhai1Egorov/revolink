package dev.wallet.dto;

import java.math.BigDecimal;

public record UpdateBalanceDto(
        BigDecimal newBalance
) {}