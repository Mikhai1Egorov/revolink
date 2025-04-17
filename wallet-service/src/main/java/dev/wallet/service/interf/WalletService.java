package dev.wallet.service.interf;

import dev.wallet.domain.Wallet;
import dev.wallet.dto.TransactionCompletedEvent;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    Optional<Wallet> findById(UUID id);
    Wallet create(String currency, BigDecimal initialBalance);
    void updateBalance(UUID id, BigDecimal newBalance);
    void deleteById(UUID id);
    void handleTransactionCompleted(TransactionCompletedEvent event);
}