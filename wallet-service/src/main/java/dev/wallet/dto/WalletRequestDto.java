package dev.wallet.dto;

import java.math.BigDecimal;

public record WalletRequestDto(String currency, BigDecimal initialBalance) {
}
