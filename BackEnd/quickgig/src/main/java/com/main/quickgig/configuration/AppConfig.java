package com.main.quickgig.configuration;


import com.main.quickgig.entity.Role;
import com.main.quickgig.entity.Roles;
import com.main.quickgig.entity.User;
import com.main.quickgig.repository.RoleRepository;
import com.main.quickgig.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
                                               UserRepository userRepository) {
        return args -> {
            Role roleAdmin = roleRepository.findByName(Roles.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(Roles.ROLE_ADMIN);
                        return roleRepository.save(newUserRole);
                    });
            Role roleEmployer = roleRepository.findByName(Roles.ROLE_EMPLOYER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(Roles.ROLE_EMPLOYER);
                        return roleRepository.save(newUserRole);
                    });
            Role roleWorker = roleRepository.findByName(Roles.ROLE_WORKER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(Roles.ROLE_WORKER);
                        return roleRepository.save(newUserRole);
                    });

            Set<Role> adminRoles = Set.of(roleAdmin, roleEmployer, roleWorker);
            Set<Role> employerRoles = Set.of(roleWorker, roleEmployer);
            Set<Role> workerRoles = Set.of(roleWorker);

            if (!userRepository.existsByEmail("admin@gmail.com")) {
                User admin = new User("admin", "admin@gmail.com", "admin");
                userRepository.save(admin);
            }
            if (!userRepository.existsByEmail("employer@gmail.com")) {
                User employer = new User("employer", "employer@gmail.com", "employer");
                userRepository.save(employer);
            }
            if (!userRepository.existsByEmail("worker@gmail.com")) {
                User worker = new User("worker", "worker@gmail.com","worker");
                userRepository.save(worker);
            }

            userRepository.findByEmail("admin@gmail.com").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });
            userRepository.findByEmail("employer@gmail.com").ifPresent(employer -> {
                employer.setRoles(employerRoles);
                userRepository.save(employer);
            });
            userRepository.findByEmail("worker@gmail.com").ifPresent(worker -> {
                worker.setRoles(workerRoles);
                userRepository.save(worker);
            });
        };
    }
}
