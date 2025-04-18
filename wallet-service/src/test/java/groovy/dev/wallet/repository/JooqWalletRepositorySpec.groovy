package groovy.dev.wallet.repository

import dev.wallet.domain.Wallet
import dev.wallet.repository.impl.JooqWalletRepository
import org.h2.jdbcx.JdbcDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import spock.lang.Specification

import java.time.Instant

class JooqWalletRepositorySpec extends Specification {
    DSLContext dsl
    JooqWalletRepository repository

    def setup() {
        def ds = new JdbcDataSource()
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_UPPER=false")
        ds.setUser("sa")
        ds.setPassword("")

        dsl = DSL.using(ds.connection, SQLDialect.POSTGRES)
        repository = new JooqWalletRepository(dsl)

        dsl.execute("CREATE SCHEMA IF NOT EXISTS public")
        dsl.execute("""
            CREATE TABLE IF NOT EXISTS public.wallet (
                id UUID PRIMARY KEY,
                currency VARCHAR(10),
                balance NUMERIC(19,2),
                created_at TIMESTAMP
            )
        """)
    }

    def "should create wallet and retrieve by id"() {
        given:
        def id = UUID.randomUUID()
        def wallet = new Wallet(id, "USD", 100.00g, Instant.now())

        when:
        def created = repository.create(wallet)
        def retrieved = repository.findById(id)

        then:
        created.id == id
        retrieved.isPresent()
        retrieved.get().id == id
        retrieved.get().balance == 100.00g
    }

    def "should update wallet balance"() {
        given:
        def id = UUID.randomUUID()
        def wallet = new Wallet(id, "EUR", 50.00g, Instant.now())
        repository.create(wallet)

        when:
        repository.updateBalance(id, 200.00g)
        def updated = repository.findById(id).get()

        then:
        updated.balance == 200.00g
    }

    def "should delete wallet by id"() {
        given:
        def id = UUID.randomUUID()
        def wallet = new Wallet(id, "GBP", 75.00g, Instant.now())
        repository.create(wallet)

        when:
        repository.deleteById(id)
        def result = repository.findById(id)

        then:
        result.isEmpty()
    }
}