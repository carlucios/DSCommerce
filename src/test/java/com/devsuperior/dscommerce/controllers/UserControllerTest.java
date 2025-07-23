package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.factories.*;
import com.devsuperior.dscommerce.repositories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;

    private Role roleUser;
    private Role roleAdmin;
    private User user;
    private User admin;

    @BeforeAll
    @Transactional
    void setUpOnce() {
        roleUser = roleRepository.findByAuthority("ROLE_CLIENT").orElseGet(() ->
                roleRepository.save(RoleFactory.user())
        );

        roleAdmin = roleRepository.findByAuthority("ROLE_ADMIN").orElseGet(() ->
                roleRepository.save(RoleFactory.admin())
        );

        user = userRepository.findWithRolesByEmail("user@gmail.com").orElseGet(() ->
                userRepository.save(UserFactory.createUser("user@gmail.com", "User", roleUser))
        );

        admin = userRepository.findWithRolesByEmail("admin@gmail.com").orElseGet(() ->
                userRepository.save(UserFactory.createUser("admin@gmail.com", "Admin", roleAdmin))
        );

    }

    @Test
    @Transactional
    void anonymousCannotAccessMeEndpoint() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void adminCanAccessMeEndpoint() throws Exception {
        mockMvc.perform(get("/users/me")
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim("username", "admin@gmail.com")
                                        .claim("sub", "admin@gmail.com")
                                )
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        ))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void userCanAccessMeEndpoint() throws Exception {
        mockMvc.perform(get("/users/me")
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim("username", "user@gmail.com")
                                        .claim("sub", "user@gmail.com")
                                )
                                .authorities(new SimpleGrantedAuthority("ROLE_CLIENT"))
                        ))
                .andExpect(status().isOk());
    }

}

