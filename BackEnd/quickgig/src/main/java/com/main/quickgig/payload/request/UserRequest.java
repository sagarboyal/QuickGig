package com.main.quickgig.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "name must not be empty")
    private String name;
    @NotBlank(message = "email must not be empty")
    private String email;
    @NotBlank(message = "password must not be empty")
    private String password;
    private String roleName;
}
