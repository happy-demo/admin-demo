package com.happyblock.admindemo.service;

import com.happyblock.admindemo.entity.UserEntity;
import com.happyblock.admindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public UserEntity createUser(UserEntity userEntity) {
        // 중복 체크
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userEntity.getUsername());
        }
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userEntity.getEmail());
        }
        return userRepository.save(userEntity);
    }
    
    public UserEntity updateUser(Long id, UserEntity userEntityDetails) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        
        // username 중복 체크 (다른 사용자가 사용 중인지)
        if (userEntityDetails.getUsername() != null &&
            !userEntity.getUsername().equals(userEntityDetails.getUsername()) &&
            userRepository.existsByUsername(userEntityDetails.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userEntityDetails.getUsername());
        }
        
        // email 중복 체크 (다른 사용자가 사용 중인지)
        if (userEntityDetails.getEmail() != null &&
            !userEntity.getEmail().equals(userEntityDetails.getEmail()) &&
            userRepository.existsByEmail(userEntityDetails.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userEntityDetails.getEmail());
        }
        
        // 업데이트
        if (userEntityDetails.getUsername() != null) {
            userEntity.setUsername(userEntityDetails.getUsername());
        }
        if (userEntityDetails.getEmail() != null) {
            userEntity.setEmail(userEntityDetails.getEmail());
        }
        
        return userRepository.save(userEntity);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
