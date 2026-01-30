package com.happyblock.admindemo.application.command;

import com.happyblock.admindemo.domain.model.User;
import com.happyblock.admindemo.infrastructure.persistence.jpa.entity.UserEntity;
import com.happyblock.admindemo.infrastructure.persistence.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUseCase {

    private final UserJpaRepository jpaRepository;

    public UserEntity createUser(UserEntity userEntity) {
        // 중복 체크
        if (jpaRepository.existsById(userEntity.getId())) {
            throw new IllegalArgumentException("User already exists with id: " + userEntity.getId());
        }
        return jpaRepository.save(userEntity);
    }

    public UserEntity updateUser(Long id, UserEntity userEntityDetails) {
        UserEntity userEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        if (userEntityDetails.getUsername() != null) {
            userEntity.setUsername(userEntityDetails.getUsername());
        }
        if (userEntityDetails.getEmail() != null) {
            userEntity.setEmail(userEntityDetails.getEmail());
        }

        return jpaRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        jpaRepository.deleteById(id);
    }

    // 도메인 모델 사용 메서드
    public User createUserDomain(User user) {
        // User 도메인 모델을 저장하는 로직
        // UserRepository에 save 메서드가 필요할 수 있음
        return user;
    }
}
