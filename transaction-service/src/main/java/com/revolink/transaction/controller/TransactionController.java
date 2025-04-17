package com.revolink.transaction.controller;

import com.revolink.transaction.dto.TransactionRequest;
import com.revolink.transaction.service.interf.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/api/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest request) {
        transactionService.processTransaction(request);
        return ResponseEntity.ok("Transaction event published");
    }
}