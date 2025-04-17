package dev.wallet.mapper;

import dev.wallet.domain.Wallet;
import dev.wallet.dto.WalletRequestDto;
import dev.wallet.dto.WalletResponseDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class WalletMapper {

    public Wallet toDomain(WalletRequestDto request) {
        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setCurrency(request.currency());
        wallet.setBalance(request.initialBalance());
        wallet.setCreatedAt(Instant.now());
        return wallet;
    }

    public WalletResponseDto toResponseDto(Wallet wallet) {
        return new WalletResponseDto(
                wallet.getId(),
                wallet.getCurrency(),
                wallet.getBalance(),
                wallet.getCreatedAt()
        );
    }
}