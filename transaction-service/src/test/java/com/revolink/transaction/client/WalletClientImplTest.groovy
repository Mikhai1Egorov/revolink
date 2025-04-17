package com.revolink.transaction.client

import com.revolink.transaction.client.impl.WalletClientImpl
import com.revolink.transaction.client.interf.WalletClient
import com.revolink.transaction.dto.WalletResponse
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class WalletClientImplTest extends Specification {

    RestTemplate restTemplate = Mock(RestTemplate)
    WalletClient client = new WalletClientImpl(restTemplate)

    def "getWallet should return wallet response from API"() {
        given:
        def walletId = UUID.randomUUID()
        def expectedResponse = new WalletResponse(walletId, 500.0, "EUR")

        when:
        def result = client.getWallet(walletId)

        then:
        1 * restTemplate.getForObject("http://localhost:8081/api/wallets/${walletId}", WalletResponse) >> expectedResponse
        result == expectedResponse
    }

    def "updateBalance should call patchForObject with correct URL"() {
        given:
        def walletId = UUID.randomUUID()
        def amount = 250.0

        when:
        client.updateBalance(walletId, amount)

        then:
        1 * restTemplate.patchForObject("http://localhost:8081/api/wallets/${walletId}/balance?amount=${amount}", null, Void)
    }
}