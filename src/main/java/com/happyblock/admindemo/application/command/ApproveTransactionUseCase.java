package com.happyblock.admindemo.application.command;

import com.happyblock.admindemo.domain.model.Transaction;
import com.happyblock.admindemo.domain.model.User;
import com.happyblock.admindemo.domain.repository.TransactionRepository;
import com.happyblock.admindemo.domain.repository.UserRepository;
import com.happyblock.admindemo.domain.vo.TransactionId;
import com.happyblock.admindemo.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * application 계층은 entity를 몰라야한다.
 * application은 “도메인을 어떻게 쓰는지”만 알아야 한다
 */
@Transactional
@RequiredArgsConstructor
public class ApproveTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public void approve(TransactionId transactionId, UserId approverId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(transactionId)));

        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(approverId)));

        // 핵심: 도메인에게 물어본다
        transaction.approveBy(approver);

        // repository.save(transaction); <- JPA면 필요 없음
    }

}
