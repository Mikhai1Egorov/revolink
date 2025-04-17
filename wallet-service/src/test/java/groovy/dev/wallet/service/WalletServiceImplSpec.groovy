package groovy.dev.wallet.service

import dev.wallet.domain.Wallet
import dev.wallet.repository.interf.WalletRepository
import dev.wallet.service.impl.WalletServiceImpl
import spock.lang.Specification

import java.time.Instant

class WalletServiceImplSpec extends Specification {

    WalletRepository repository = Mock()
    WalletServiceImpl service = new WalletServiceImpl(repository)

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

    def "should update balance"() {
        given:
        def id = UUID.randomUUID()
        def newBalance = new BigDecimal("200.00")

        when:
        service.updateBalance(id, newBalance)

        then:
        1 * repository.updateBalance(id, newBalance)
    }

    def "should delete wallet by id"() {
        given:
        def id = UUID.randomUUID()

        when:
        service.deleteById(id)

        then:
        1 * repository.deleteById(id)
    }
}