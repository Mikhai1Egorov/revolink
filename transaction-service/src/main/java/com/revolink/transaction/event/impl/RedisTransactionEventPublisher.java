package com.revolink.transaction.event.impl;

import com.revolink.transaction.event.TransactionCompletedEvent;
import com.revolink.transaction.event.interf.TransactionEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTransactionEventPublisher implements TransactionEventPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CHANNEL = "transaction.completed";

    public RedisTransactionEventPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publish(TransactionCompletedEvent event) {
        redisTemplate.convertAndSend(CHANNEL, event);
    }
}