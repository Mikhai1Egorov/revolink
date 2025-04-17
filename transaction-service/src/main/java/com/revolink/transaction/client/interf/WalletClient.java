package com.revolink.transaction.client.interf;

import com.revolink.transaction.dto.WalletResponse;

import java.util.UUID;

public interface WalletClient {
    WalletResponse getWallet(UUID walletId);
    void updateBalance(UUID walletId, double newBalance);
}