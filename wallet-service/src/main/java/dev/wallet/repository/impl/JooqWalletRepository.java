package dev.wallet.repository.impl;

import dev.revolink.wallet.jooq.tables.records.WalletRecord;
import dev.wallet.domain.Wallet;
import dev.wallet.repository.interf.WalletRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static dev.revolink.wallet.jooq.tables.Wallet.WALLET;

@Repository
public class JooqWalletRepository implements WalletRepository {

    private final DSLContext dsl;

    public JooqWalletRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return dsl.selectFrom(WALLET)
                .where(WALLET.ID.eq(id))
                .fetchOptional()
                .map(this::mapToDomain);
    }

    @Override
    public Wallet create(Wallet wallet) {
        dsl.insertInto(WALLET)
                .set(WALLET.ID, wallet.getId())
                .set(WALLET.CURRENCY, wallet.getCurrency())
                .set(WALLET.BALANCE, wallet.getBalance())
                .set(WALLET.CREATED_AT, wallet.getCreatedAt() != null
                        ? wallet.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        : null)
                .execute();
        return wallet;
    }


    @Override
    public void updateBalance(UUID id, BigDecimal newBalance) {
        dsl.update(WALLET)
                .set(WALLET.BALANCE, newBalance)
                .where(WALLET.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteById(UUID id) {
        dsl.deleteFrom(WALLET)
                .where(WALLET.ID.eq(id))
                .execute();
    }

    private Wallet mapToDomain(WalletRecord record) {
        Wallet wallet = new Wallet();
        wallet.setId(record.getId());
        wallet.setCurrency(record.getCurrency());
        wallet.setBalance(record.getBalance());
        wallet.setCreatedAt(record.getCreatedAt() != null ? record.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant() : null);
        return wallet;
    }
}