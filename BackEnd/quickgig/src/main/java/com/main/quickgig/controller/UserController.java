package com.main.quickgig.controller;

import com.main.quickgig.constraint.AppConstraint;
import com.main.quickgig.payload.request.UserRequest;
import com.main.quickgig.payload.request.UserUpdateRequest;
import com.main.quickgig.payload.response.PagedResponse;
import com.main.quickgig.payload.response.UserResponse;
import com.main.quickgig.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<PagedResponse<UserResponse>> getUsers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstraint.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstraint.DEFAULT_SORT_BY_USER, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstraint.SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(userService.getUsers(pageNumber, pageSize, sortBy, sortOrder));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserUpdateRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
