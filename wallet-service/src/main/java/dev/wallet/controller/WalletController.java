package dev.wallet.controller;

import dev.wallet.domain.Wallet;
import dev.wallet.dto.UpdateBalanceDto;
import dev.wallet.dto.WalletRequestDto;
import dev.wallet.dto.WalletResponseDto;
import dev.wallet.mapper.WalletMapper;
import dev.wallet.service.interf.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    public WalletController(WalletService walletService, WalletMapper walletMapper) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponseDto> getById(@PathVariable("id") UUID id) {
        return walletService.findById(id)
                .map(walletMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WalletResponseDto> create(@RequestBody WalletRequestDto request) {
        Wallet created = walletService.create(request.currency(), request.initialBalance());
        return ResponseEntity.ok(walletMapper.toResponseDto(created));
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable UUID id, @RequestBody UpdateBalanceDto update) {
        walletService.updateBalance(id, update.newBalance());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        walletService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}