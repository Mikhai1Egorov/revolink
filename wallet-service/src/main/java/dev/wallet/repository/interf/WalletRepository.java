package dev.wallet.repository.interf;

import dev.wallet.domain.Wallet;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Optional<Wallet> findById(UUID id);
    Wallet create(Wallet wallet);
    void updateBalance(UUID id, BigDecimal newBalance);
    void deleteById(UUID id);
}