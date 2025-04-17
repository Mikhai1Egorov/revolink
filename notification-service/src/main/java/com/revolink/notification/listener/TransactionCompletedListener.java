package com.revolink.notification.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolink.notification.model.TransactionCompletedEvent;
import com.revolink.notification.service.interf.NotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionCompletedListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody());
            TransactionCompletedEvent event = objectMapper.readValue(json, TransactionCompletedEvent.class);
            notificationService.notify(event);
        } catch (Exception e) {
            log.error("❌ Error Redis", e);
        }
    }
}