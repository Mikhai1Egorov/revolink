package com.revolink.transaction.client.impl;

import com.revolink.transaction.client.interf.WalletClient;
import com.revolink.transaction.dto.WalletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class WalletClientImpl implements WalletClient {
    private final RestTemplate restTemplate;
    private static final String WALLET_SERVICE_URL = "http://localhost:8081/api/wallets";

    public WalletClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WalletResponse getWallet(UUID walletId) {
        return restTemplate.getForObject(WALLET_SERVICE_URL + "/" + walletId, WalletResponse.class);
    }

    @Override
    public void updateBalance(UUID walletId, double newBalance) {
        restTemplate.patchForObject(WALLET_SERVICE_URL + "/" + walletId + "/balance?amount=" + newBalance, null, Void.class);
    }
}