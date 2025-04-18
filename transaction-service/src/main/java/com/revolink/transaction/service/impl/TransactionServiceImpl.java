package com.revolink.transaction.service.impl;

import com.revolink.transaction.dto.TransactionRequest;
import com.revolink.transaction.event.TransactionCompletedEvent;
import com.revolink.transaction.event.interf.TransactionEventPublisher;
import com.revolink.transaction.metics.TransactionMetricsService;
import com.revolink.transaction.service.interf.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionEventPublisher publisher;
    private final TransactionMetricsService metricsService;

    @Override
    public void processTransaction(TransactionRequest request) {
        TransactionCompletedEvent event = new TransactionCompletedEvent(
                request.getFromWalletId(),
                request.getToWalletId(),
                request.getAmount(),
                request.getCurrency()
        );

        publisher.publish(event);
        metricsService.incrementTransactionCreated();
    }
}