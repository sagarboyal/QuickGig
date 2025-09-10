package com.main.quickgig.serviceImpl;

import com.main.quickgig.payload.request.SignInRequest;
import com.main.quickgig.payload.request.UserRequest;
import com.main.quickgig.payload.response.MessageResponse;
import com.main.quickgig.payload.response.SignInResponse;
import com.main.quickgig.payload.response.UserResponse;
import com.main.quickgig.repository.UserRepository;
import com.main.quickgig.service.AuthService;
import com.main.quickgig.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final com.main.quickgig.security.jwt.JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        return SignInResponse.build(userDetails.getUsername(), jwtToken);
    }

    @Override
    public MessageResponse signUp(UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return new MessageResponse("Organisation Registration Successful! with mail: " + userResponse.email());
    }

    @Override
    public UserResponse getLoggedInUser() {
        return null;
    }
}
