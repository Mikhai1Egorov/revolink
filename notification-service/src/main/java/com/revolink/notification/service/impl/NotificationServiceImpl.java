package com.revolink.notification.service.impl;

import com.revolink.notification.metrics.NotificationMetricsService;
import com.revolink.notification.model.TransactionCompletedEvent;
import com.revolink.notification.service.interf.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMetricsService metricsService;

    @Override
    public void notify(TransactionCompletedEvent event) {
        metricsService.record(() -> {
            try {
                log.info("🔔 Notification: transaction {} {} from {} to {}",
                        event.getAmount(), event.getCurrency(),
                        event.getFromWalletId(), event.getToWalletId());
                metricsService.incrementReceived();
            } catch (Exception e) {
                log.error("❌ Failed to process notification event", e);
                metricsService.incrementFailed();
            }
        });
    }
}