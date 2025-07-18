package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findWithRolesByEmailShouldReturnUserWithRoles() {
        Role role = Role.builder()
                .authority("ROLE_ADMIN")
                .build();

        entityManager.persist(role);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .name("Maria")
                .email("maria@example.com")
                .password("123456")
                .birthDate(Instant.parse("2000-01-01T00:00:00Z"))
                .roles(roles)
                .build();

        role.setUsers(Set.of(user));

        userRepository.save(user);

        Optional<User> result = userRepository.findWithRolesByEmail("maria@example.com");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Maria", result.get().getName());
        Assertions.assertFalse(result.get().getRoles().isEmpty());
        Assertions.assertTrue(result.get().getRoles().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
    }
}
