package groovy.dev.wallet.controller

import dev.wallet.controller.WalletController
import dev.wallet.domain.Wallet
import dev.wallet.dto.UpdateBalanceDto
import dev.wallet.dto.WalletRequestDto
import dev.wallet.dto.WalletResponseDto
import dev.wallet.mapper.WalletMapper
import dev.wallet.service.interf.WalletService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.time.Instant

class WalletControllerSpec extends Specification {
    WalletService walletService = Mock()
    WalletMapper walletMapper = Mock()
    WalletController controller = new WalletController(walletService, walletMapper)

    def "should return wallet by id"() {
        given:
        def id = UUID.randomUUID()
        def wallet = new Wallet(id, "USD", new BigDecimal("100.00"), Instant.now())
        def responseDto = new WalletResponseDto(id, "USD", new BigDecimal("100.00"), Instant.now())

        when:
        def result = controller.getById(id)

        then:
        1 * walletService.findById(id) >> Optional.of(wallet)
        1 * walletMapper.toResponseDto(wallet) >> responseDto
        result == ResponseEntity.ok(responseDto)
    }

    def "should return 404 if wallet not found"() {
        given:
        def id = UUID.randomUUID()

        when:
        def result = controller.getById(id)

        then:
        1 * walletService.findById(id) >> Optional.empty()
        0 * walletMapper.toResponseDto(_)
        result == ResponseEntity.notFound().build()
    }

    def "should create a new wallet"() {
        given:
        def request = new WalletRequestDto("USD", new BigDecimal("50.00"))
        def wallet = new Wallet(UUID.randomUUID(), "USD", new BigDecimal("50.00"), Instant.now())
        def responseDto = new WalletResponseDto(wallet.id, "USD", wallet.balance, wallet.createdAt)

        when:
        def result = controller.create(request)

        then:
        1 * walletService.create("USD", new BigDecimal("50.00")) >> wallet
        1 * walletMapper.toResponseDto(wallet) >> responseDto
        result == ResponseEntity.ok(responseDto)
    }

    def "should update wallet balance"() {
        given:
        def id = UUID.randomUUID()
        def update = new UpdateBalanceDto(new BigDecimal("150.00"))

        when:
        def result = controller.updateBalance(id, update)

        then:
        1 * walletService.updateBalance(id, update.newBalance())
        result == ResponseEntity.ok().build()
    }

    def "should delete wallet"() {
        given:
        def id = UUID.randomUUID()

        when:
        def result = controller.delete(id)

        then:
        1 * walletService.deleteById(id)
        result == ResponseEntity.noContent().build()
    }
}