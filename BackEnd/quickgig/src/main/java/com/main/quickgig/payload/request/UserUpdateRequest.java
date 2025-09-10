package com.main.quickgig.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotNull(message = "user id must not be empty")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String roleName;
}
