package com.revolink.notification.service

import com.revolink.notification.metrics.NotificationMetricsService
import com.revolink.notification.model.TransactionCompletedEvent
import com.revolink.notification.service.impl.NotificationServiceImpl
import spock.lang.Specification

class NotificationServiceImplSpec extends Specification {

    def metricsService = Mock(NotificationMetricsService)
    def service = new NotificationServiceImpl(metricsService)

    def sampleEvent = new TransactionCompletedEvent(
            UUID.randomUUID(),
            UUID.randomUUID(),
            100.0,
            "EUR"
    )

    def "should increment received metric when notification succeeds"() {
        given:
        Runnable[] capturedRunnable = new Runnable[1]

        when:
        1 * metricsService.record(_ as Runnable) >> { Runnable r -> capturedRunnable[0] = r }
        service.notify(sampleEvent)
        capturedRunnable[0].run()

        then:
        1 * metricsService.incrementReceived()
        0 * metricsService.incrementFailed()
    }
}