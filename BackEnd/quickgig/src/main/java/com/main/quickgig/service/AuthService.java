package com.main.quickgig.service;


import com.main.quickgig.payload.request.SignInRequest;
import com.main.quickgig.payload.request.UserRequest;
import com.main.quickgig.payload.response.MessageResponse;
import com.main.quickgig.payload.response.SignInResponse;
import com.main.quickgig.payload.response.UserResponse;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);
    MessageResponse signUp(UserRequest userRequest);
    UserResponse getLoggedInUser();
}
