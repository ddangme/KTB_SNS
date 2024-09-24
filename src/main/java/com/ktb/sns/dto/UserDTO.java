package com.ktb.sns.dto;

import com.ktb.sns.model.User;
import com.ktb.sns.model.constants.UserRole;

import java.time.LocalDateTime;

public record UserDTO(
        String loginId,
        String password,
        String email,
        String nickname,
        LocalDateTime createdAt,
        UserRole userRole) {

    public static UserDTO of(String userId, String userPassword, String email, String nickname) {
        return new UserDTO(userId, userPassword, email, nickname, null, null);
    }

    public static UserDTO of(String userId, String userPassword, String email, String nickname, LocalDateTime createdAt) {
        return new UserDTO(userId, userPassword, email, nickname, createdAt, null);
    }

    public static UserDTO from(User entity) {
        return new UserDTO(
                entity.getLoginId(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getCreatedAt(),
                null
        );
    }

    public User toEntity() {
        return User.of(
                loginId,
                password,
                email,
                nickname,
                userRole
        );
    }
}
