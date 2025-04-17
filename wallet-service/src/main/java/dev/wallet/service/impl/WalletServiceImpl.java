package dev.wallet.service.impl;

import dev.wallet.domain.Wallet;
import dev.wallet.repository.interf.WalletRepository;
import dev.wallet.service.interf.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Wallet create(String currency, BigDecimal initialBalance) {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setCurrency(currency);
        wallet.setBalance(initialBalance);
        wallet.setCreatedAt(Instant.now());
        return repository.create(wallet);
    }

    @Override
    public void updateBalance(UUID id, BigDecimal newBalance) {
        repository.updateBalance(id, newBalance);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}