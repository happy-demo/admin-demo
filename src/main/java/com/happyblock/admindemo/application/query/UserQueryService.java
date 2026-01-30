package com.happyblock.admindemo.application.query;

import com.happyblock.admindemo.domain.model.User;
import com.happyblock.admindemo.domain.repository.UserRepository;
import com.happyblock.admindemo.domain.vo.UserId;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.UserEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {

    private final UserJpaRepository jpaRepository;
    private final UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return jpaRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return jpaRepository.findById(id);
    }

    // 도메인 모델 조회 메서드
    public Optional<User> findDomainById(UserId id) {
        return userRepository.findById(id);
    }

    public List<User> findAllDomain() {
        // UserRepository에 findAll이 없으면 추가 필요
        // 일단 JPA로 조회 후 변환
        return jpaRepository.findAll()
                .stream()
                .map(entity -> userRepository.findById(new UserId(entity.getId())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
