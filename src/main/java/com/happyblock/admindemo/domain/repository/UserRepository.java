package com.happyblock.admindemo.domain.repository;

import com.happyblock.admindemo.domain.model.User;
import com.happyblock.admindemo.domain.vo.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findById(UserId id);
}
