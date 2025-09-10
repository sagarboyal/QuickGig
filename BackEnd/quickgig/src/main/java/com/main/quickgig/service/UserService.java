package com.main.quickgig.service;

import com.main.quickgig.payload.request.UserRequest;
import com.main.quickgig.payload.request.UserUpdateRequest;
import com.main.quickgig.payload.response.PagedResponse;
import com.main.quickgig.payload.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(UserUpdateRequest userUpdateRequest);
    String deleteUser(Long id);
    PagedResponse<UserResponse> getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
