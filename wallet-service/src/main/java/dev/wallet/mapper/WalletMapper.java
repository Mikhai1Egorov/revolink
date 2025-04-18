package dev.wallet.mapper;

import dev.wallet.domain.Wallet;
import dev.wallet.dto.WalletResponseDto;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public WalletResponseDto toResponseDto(Wallet wallet) {
        return new WalletResponseDto(
                wallet.getId(),
                wallet.getCurrency(),
                wallet.getBalance(),
                wallet.getCreatedAt()
        );
    }
}