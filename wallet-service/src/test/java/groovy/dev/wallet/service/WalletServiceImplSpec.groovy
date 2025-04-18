package groovy.dev.wallet.service

import dev.wallet.domain.Wallet
import dev.wallet.dto.TransactionCompletedEvent
import dev.wallet.metrics.WalletMetricsService
import dev.wallet.repository.interf.WalletRepository
import dev.wallet.service.impl.WalletServiceImpl
import spock.lang.Specification

import java.time.Instant

class WalletServiceImplSpec extends Specification {

    WalletRepository repository = Mock()
    WalletMetricsService metrics = Mock()
    WalletServiceImpl service = new WalletServiceImpl(repository, metrics)

    def "should create wallet"() {
        given:
        def currency = "USD"
        def balance = new BigDecimal("100.00")
        def wallet = new Wallet(UUID.randomUUID(), currency, balance, Instant.now())
        repository.create(_ as Wallet) >> wallet

        when:
        def result = service.create(currency, balance)

        then:
        result.id != null
        result.currency == currency
        result.balance == balance
        1 * repository.create(_) >> wallet
    }

    def "should find wallet by id"() {
        given:
        def id = UUID.randomUUID()
        def wallet = new Wallet(id, "USD", new BigDecimal("50.00"), Instant.now())

        when:
        def result = service.findById(id)

        then:
        1 * repository.findById(_ as UUID) >> Optional.of(wallet)
        result != null
        result.isPresent()
        result.get().id == id
    }

    def "should update balance and increment metric"() {
        given:
        def id = UUID.randomUUID()
        def newBalance = new BigDecimal("200.00")

        when:
        service.updateBalance(id, newBalance)

        then:
        1 * repository.updateBalance(id, newBalance)
        1 * metrics.incrementBalanceUpdate()
    }

    def "should delete wallet by id"() {
        given:
        def id = UUID.randomUUID()

        when:
        service.deleteById(id)

        then:
        1 * repository.deleteById(id)
    }

    def "should update balances and record metrics on transaction.completed event"() {
        given:
        def fromId = UUID.randomUUID()
        def toId = UUID.randomUUID()
        def amount = 50.00G

        def fromWallet = new Wallet(fromId, "EUR", 200.00G)
        def toWallet = new Wallet(toId, "EUR", 100.00G)

        repository.findById(fromId) >> Optional.of(fromWallet)
        repository.findById(toId) >> Optional.of(toWallet)

        def event = new TransactionCompletedEvent(fromId, toId, amount, "EUR")

        when:
        Runnable runnable
        1 * metrics.recordTransactionHandling(_ as Runnable) >> { Runnable r -> runnable = r }

        // вызвать handle
        service.handleTransactionCompleted(event)

        // выполнить Runnable вручную, чтобы зашли внутрь updateBalance
        runnable.run()

        then:
        1 * repository.updateBalance(fromId, 150.00G)
        1 * repository.updateBalance(toId, 150.00G)
        2 * metrics.incrementBalanceUpdate()
    }

}