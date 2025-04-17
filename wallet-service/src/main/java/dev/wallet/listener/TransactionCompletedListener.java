package dev.wallet.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wallet.dto.TransactionCompletedEvent;
import dev.wallet.service.interf.WalletService;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionCompletedListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final WalletService walletService;
    private final RedisMessageListenerContainer container;

    @PostConstruct
    public void subscribe() {
        container.addMessageListener(this, new ChannelTopic("transaction.completed"));
        log.info("Subscribed to Redis channel: transaction.completed");
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            TransactionCompletedEvent event = objectMapper.readValue(body, TransactionCompletedEvent.class);
            log.info("Received transaction.completed event: {}", event);

            walletService.handleTransactionCompleted(event);

        } catch (Exception e) {
            log.error("Error processing transaction.completed event", e);
        }
    }
}