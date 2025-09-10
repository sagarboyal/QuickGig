package com.main.quickgig.payload.response;

public record SignInResponse(
        String email,
        String token
) {
    public static SignInResponse build(String email, String token) {
        return new SignInResponse(email, token);
    }
}