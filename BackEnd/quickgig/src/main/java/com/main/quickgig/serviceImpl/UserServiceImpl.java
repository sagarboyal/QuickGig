package com.main.quickgig.serviceImpl;

import com.main.quickgig.entity.Role;
import com.main.quickgig.entity.Roles;
import com.main.quickgig.entity.User;
import com.main.quickgig.exception.custom.APIExceptions;
import com.main.quickgig.payload.request.UserRequest;
import com.main.quickgig.payload.request.UserUpdateRequest;
import com.main.quickgig.payload.response.PagedResponse;
import com.main.quickgig.payload.response.UserResponse;
import com.main.quickgig.repository.RoleRepository;
import com.main.quickgig.repository.UserRepository;
import com.main.quickgig.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail()))
            throw new APIExceptions("Email already exists", HttpStatus.CONFLICT);

        User user = new User(userRequest.getName(),  userRequest.getEmail(), userRequest.getPassword());
        Role defaultRole = roleRepository.findByName(Roles.ROLE_WORKER)
                    .orElseThrow(() -> new APIExceptions("Invalid Role"));
        user.setRoles(Set.of(defaultRole));
        user = userRepository.save(user);
        return UserResponse.fromEntity(user, extractRoles(user.getRoles()));
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest req) {
        User data = findUser(req.getId());
        if(req.getName() != null && !req.getName().equals(data.getName()))
            data.setName(req.getName());
        if(req.getEmail() != null && !req.getEmail().equals(data.getEmail())){
            if(userRepository.existsByEmail(req.getEmail()))
                throw new APIExceptions("Email already exists", HttpStatus.CONFLICT);
            data.setEmail(req.getEmail());
        }
        if(req.getPassword() != null && !req.getPassword().equals(data.getPassword())){
            data.setPassword(req.getPassword());
        }

        data.setUpdatedAt(LocalDateTime.now());
        data = userRepository.save(data);
        return UserResponse.fromEntity(data, extractRoles(data.getRoles()));
    }

    @Override
    public String deleteUser(Long id) {
        User data = findUser(id);
        userRepository.delete(data);
        return "user with id: " + id + " has been deleted";
    }

    @Override
    public PagedResponse<UserResponse> getUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageDetails);
        return getPagedResponse(userPage);
    }

    private String extractRoles(Set<Role> user) {
        StringBuilder sb = new StringBuilder();
        for (Role role : user) {
            sb.append(role.getName()).append(", ");
        }
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new APIExceptions("User not found", HttpStatus.NOT_FOUND));
    }

    private PagedResponse<UserResponse> getPagedResponse(Page<User> userPage) {
        List<User> users = userPage.getContent();

        if (users.isEmpty())
            throw new APIExceptions("No Tenant found!");

        List<UserResponse> productDTOS = users.stream()
                .map(user -> UserResponse.fromEntity(user, extractRoles(user.getRoles())))
                .toList();

        return PagedResponse.<UserResponse>builder()
                .content(productDTOS)
                .pageNumber(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .lastPage(userPage.isLast())
                .build();
    }
}
