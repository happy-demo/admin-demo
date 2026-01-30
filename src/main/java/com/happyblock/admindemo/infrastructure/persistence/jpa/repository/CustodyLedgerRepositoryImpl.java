package com.happyblock.admindemo.infrastructure.persistence.jpa.repository;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.repository.CustodyLedgerRepository;
import com.happyblock.admindemo.domain.vo.CustodyLedgerId;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.CustodyLedgerEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.mapper.CustodyLedgerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CustodyLedgerRepositoryImpl implements CustodyLedgerRepository {

    private final CustodyLedgerJpaRepository jpaRepository;
    private final CustodyLedgerMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public CustodyLedger find(CustodyLedgerId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("CustodyLedger not found with id: " + id.value()));
    }

    @Override
    @Transactional
    public void save(CustodyLedger ledger) {
        CustodyLedgerEntity entity = ledger.getId() != null && ledger.getId().value() != null
                ? jpaRepository.findById(ledger.getId().value())
                        .orElseGet(() -> mapper.toEntity(ledger))
                : mapper.toEntity(ledger);

        mapper.apply(ledger, entity);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTransactionId(TransactionId transactionId) {
        return jpaRepository.existsByTransactionId(transactionId.value());
    }
}
