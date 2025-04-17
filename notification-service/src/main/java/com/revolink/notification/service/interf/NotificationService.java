package com.revolink.notification.service.interf;

import com.revolink.notification.model.TransactionCompletedEvent;

public interface NotificationService {
    void notify(TransactionCompletedEvent event);
}