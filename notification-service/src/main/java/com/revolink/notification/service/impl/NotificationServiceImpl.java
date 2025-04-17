package com.revolink.notification.service.impl;

import com.revolink.notification.model.TransactionCompletedEvent;
import com.revolink.notification.service.interf.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notify(TransactionCompletedEvent event) {
        log.info("🔔 Notification: transaction {} {} from {} to {}",
                 event.getAmount(), event.getCurrency(),
                 event.getFromWalletId(), event.getToWalletId());
    }
}