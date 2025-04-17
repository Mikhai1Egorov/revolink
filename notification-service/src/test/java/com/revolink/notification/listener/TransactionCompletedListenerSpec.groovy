package com.revolink.notification.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.revolink.notification.model.TransactionCompletedEvent
import com.revolink.notification.service.interf.NotificationService
import org.springframework.data.redis.connection.Message
import spock.lang.Specification

class TransactionCompletedListenerSpec extends Specification {

    ObjectMapper objectMapper = new ObjectMapper()
    NotificationService notificationService = Mock(NotificationService)
    TransactionCompletedListener listener = new TransactionCompletedListener(objectMapper, notificationService)

    def "should call notify when valid JSON message is received"() {
        given:
        def event = new TransactionCompletedEvent(
                fromWalletId: UUID.randomUUID(),
                toWalletId: UUID.randomUUID(),
                amount: 100.0,
                currency: "EUR"
        )
        def json = objectMapper.writeValueAsString(event)
        def message = new DummyRedisMessage(json.bytes)

        when:
        listener.onMessage(message, null)

        then:
        1 * notificationService.notify({
            it.fromWalletId == event.fromWalletId &&
                    it.toWalletId == event.toWalletId &&
                    it.amount == event.amount &&
                    it.currency == event.currency
        })
    }

    def "should not crash on invalid JSON message"() {
        given:
        def message = new DummyRedisMessage("INVALID_JSON".bytes)

        when:
        listener.onMessage(message, null)

        then:
        noExceptionThrown()
        0 * notificationService.notify(_)
    }

    static class DummyRedisMessage implements Message {
        final byte[] body

        DummyRedisMessage(byte[] body) {
            this.body = body
        }

        @Override
        byte[] getBody() {
            return body
        }

        @Override
        byte[] getChannel() {
            return "transaction.completed".bytes
        }
    }
}