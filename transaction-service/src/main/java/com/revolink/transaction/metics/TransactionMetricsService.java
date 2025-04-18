package com.revolink.transaction.metics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


import java.time.Duration;

@Service
public class TransactionMetricsService {

    private final MeterRegistry meterRegistry;
    private Counter transactionCreatedCounter;
    private Timer transactionProcessingTimer;

    public TransactionMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void initMetrics() {
        this.transactionCreatedCounter = Counter.builder("transaction.created.count")
                .description("Total number of transactions created")
                .tag("service", "transaction-service")
                .register(meterRegistry);

        this.transactionProcessingTimer = Timer.builder("transaction.processing.time")
                .description("Time taken to process transaction")
                .tag("service", "transaction-service")
                .publishPercentiles(0.5, 0.95, 0.99)
                .maximumExpectedValue(Duration.ofSeconds(5))
                .register(meterRegistry);
    }

    public void incrementTransactionCreated() {
        transactionCreatedCounter.increment();
    }

    public void recordTransactionProcessing(Runnable runnable) {
        transactionProcessingTimer.record(runnable);
    }
}
