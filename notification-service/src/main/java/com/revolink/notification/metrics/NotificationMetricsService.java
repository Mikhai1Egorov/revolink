package com.revolink.notification.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class NotificationMetricsService {
    private final MeterRegistry registry;
    private Counter receivedCounter;
    private Counter failedCounter;
    private Timer processingTimer;

    public NotificationMetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        this.receivedCounter = Counter.builder("notification.received.count")
                .description("Number of successfully processed notifications")
                .tag("service", "notification-service")
                .register(registry);

        this.failedCounter = Counter.builder("notification.failed.count")
                .description("Number of failed notifications")
                .tag("service", "notification-service")
                .register(registry);

        this.processingTimer = Timer.builder("notification.processing.time")
                .description("Time taken to process notification")
                .publishPercentiles(0.5, 0.95, 0.99)
                .maximumExpectedValue(Duration.ofSeconds(5))
                .tag("service", "notification-service")
                .register(registry);
    }

    public void incrementReceived() {
        receivedCounter.increment();
    }
    public void incrementFailed() {
        failedCounter.increment();
    }
    public void record(Runnable runnable) {
        processingTimer.record(runnable);
    }
}