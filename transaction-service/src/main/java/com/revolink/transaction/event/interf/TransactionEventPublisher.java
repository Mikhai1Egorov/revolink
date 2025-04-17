package com.revolink.transaction.event.interf;

import com.revolink.transaction.event.TransactionCompletedEvent;

public interface TransactionEventPublisher {
    void publish(TransactionCompletedEvent event);
}