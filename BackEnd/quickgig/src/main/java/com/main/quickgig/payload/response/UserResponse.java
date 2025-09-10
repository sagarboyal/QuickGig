package com.main.quickgig.payload.response;

import com.main.quickgig.entity.User;
import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String name,
        String email,
        String password,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String roles
) {
    public static UserResponse fromEntity(User user, String roles) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                roles
        );
    }
}
