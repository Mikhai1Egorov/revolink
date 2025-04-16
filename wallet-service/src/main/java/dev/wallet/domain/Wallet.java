package dev.wallet.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Wallet {
    private UUID id;
    private String currency;
    private BigDecimal balance;
    private Instant createdAt;
}