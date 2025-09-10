package com.main.quickgig.security.authService;

import com.main.quickgig.entity.User;
import com.main.quickgig.exception.custom.APIExceptions;
import com.main.quickgig.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new APIExceptions("Tenant with email [" + email + "] not found", HttpStatus.BAD_REQUEST));
        return SecurityDetails.build(user);
    }
}

