package com.revolink.transaction.service

import com.revolink.transaction.dto.TransactionRequest
import com.revolink.transaction.event.TransactionCompletedEvent
import com.revolink.transaction.event.interf.TransactionEventPublisher
import com.revolink.transaction.metics.TransactionMetricsService
import com.revolink.transaction.service.impl.TransactionServiceImpl
import com.revolink.transaction.service.interf.TransactionService
import spock.lang.Specification

class TransactionServiceImplTest extends Specification {

    TransactionEventPublisher publisher = Mock()
    TransactionMetricsService metricsService = Mock()
    TransactionService service = new TransactionServiceImpl(publisher, metricsService)

    def "should publish event with correct data and increment metric"() {
        given:
        def request = new TransactionRequest(UUID.randomUUID(), UUID.randomUUID(), 100.0, "EUR")

        when:
        service.processTransaction(request)

        then:
        1 * publisher.publish({
            it.fromWalletId == request.fromWalletId &&
                    it.toWalletId == request.toWalletId &&
                    it.amount == request.amount &&
                    it.currency == request.currency
        } as TransactionCompletedEvent)

        1 * metricsService.incrementTransactionCreated()
    }
}