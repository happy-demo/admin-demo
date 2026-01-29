package com.happyblock.admindemo.controller;

import com.happyblock.admindemo.entity.UserEntity;
import com.happyblock.admindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return ResponseEntity.ok(userEntities);
    }
    
    // ID로 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    // 사용자 생성
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {
        // 중복 체크
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists: " + userEntity.getUsername());
        }
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists: " + userEntity.getEmail());
        }
        
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserEntity);
    }
    
    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntityDetails) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        UserEntity userEntity = userOptional.get();
        
        // username 중복 체크 (다른 사용자가 사용 중인지)
        if (userEntityDetails.getUsername() != null &&
            !userEntity.getUsername().equals(userEntityDetails.getUsername()) &&
            userRepository.existsByUsername(userEntityDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists: " + userEntityDetails.getUsername());
        }
        
        // email 중복 체크 (다른 사용자가 사용 중인지)
        if (userEntityDetails.getEmail() != null &&
            !userEntity.getEmail().equals(userEntityDetails.getEmail()) &&
            userRepository.existsByEmail(userEntityDetails.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists: " + userEntityDetails.getEmail());
        }
        
        // 업데이트
        if (userEntityDetails.getUsername() != null) {
            userEntity.setUsername(userEntityDetails.getUsername());
        }
        if (userEntityDetails.getEmail() != null) {
            userEntity.setEmail(userEntityDetails.getEmail());
        }
        
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return ResponseEntity.ok(updatedUserEntity);
    }
    
    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        userRepository.deleteById(id);
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
