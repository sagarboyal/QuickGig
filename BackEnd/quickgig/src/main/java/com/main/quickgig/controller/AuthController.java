package com.main.quickgig.controller;

import com.main.quickgig.payload.request.SignInRequest;
import com.main.quickgig.payload.request.UserRequest;
import com.main.quickgig.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest loginRequest) {
       return ResponseEntity.ok(authService.signIn(loginRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.signUp(userRequest));
    }
}
