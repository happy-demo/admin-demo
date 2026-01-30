package com.happyblock.admindemo.domain.repository;

import com.happyblock.admindemo.domain.model.CustodyLedger;
import com.happyblock.admindemo.domain.vo.CustodyLedgerId;
import com.happyblock.admindemo.domain.vo.TransactionId;
import org.springframework.stereotype.Repository;

/**
 * UseCase가 의존하는 유일한 저장소 인터페이스
 *
 * JPA 모름
 * Entity 모름
 * SQL 모름
 * 도메인 언어만 사용
 */
@Repository
public interface CustodyLedgerRepository {

    CustodyLedger find(CustodyLedgerId id);

    void save(CustodyLedger ledger);

    boolean existsByTransactionId(TransactionId transactionId);
}
