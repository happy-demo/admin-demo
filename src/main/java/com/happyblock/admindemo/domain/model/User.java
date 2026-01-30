package com.happyblock.admindemo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class User {

    private final Long id;
    private final String username;
    private final String email;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
}
