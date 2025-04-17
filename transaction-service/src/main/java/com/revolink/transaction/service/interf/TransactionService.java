package com.revolink.transaction.service.interf;

import com.revolink.transaction.dto.TransactionRequest;

public interface TransactionService {
    void processTransaction(TransactionRequest request);
}