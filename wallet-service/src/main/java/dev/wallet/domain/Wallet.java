package dev.wallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Wallet {
    private UUID id;
    private String currency;
    private BigDecimal balance;
    private Instant createdAt;

    public Wallet(UUID id, String currency, BigDecimal balance, Instant createdAt) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Wallet(UUID id, String currency, BigDecimal balance) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
    }
}