package dev.wallet.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WalletMetricsService {

    private final MeterRegistry registry;

    private Counter balanceUpdatedCounter;
    private Timer transactionHandleTimer;

    public WalletMetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        this.balanceUpdatedCounter = Counter.builder("wallet.balance.updated.count")
                .description("Number of times wallet balance was updated")
                .tag("service", "wallet-service")
                .register(registry);

        this.transactionHandleTimer = Timer.builder("wallet.handle.transaction.time")
                .description("Time taken to process transaction.completed event")
                .publishPercentiles(0.5, 0.95, 0.99)
                .maximumExpectedValue(Duration.ofSeconds(5))
                .tag("service", "wallet-service")
                .register(registry);
    }

    public void incrementBalanceUpdate() {
        balanceUpdatedCounter.increment();
    }

    public void recordTransactionHandling(Runnable runnable) {
        transactionHandleTimer.record(runnable);
    }
}