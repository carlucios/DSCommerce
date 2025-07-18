package com.devsuperior.dscommerce.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class UserTests {

    @Test
    public void userShouldStoreDataCorrectly() {
        Role role = Role.builder().id(1L).authority("ROLE_CLIENT").build();

        User user = User.builder()
                .id(10L)
                .name("Maria Silva")
                .email("maria@example.com")
                .password("123456")
                .phone("(99) 9999-9999")
                .birthDate(Instant.parse("1990-01-01T00:00:00Z"))
                .roles(Set.of(role))
                .build();

        assertEquals(10L, user.getId());
        assertEquals("Maria Silva", user.getName());
        assertEquals("maria@example.com", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertEquals("(99) 9999-9999", user.getPhone());
        assertEquals(Instant.parse("1990-01-01T00:00:00Z"), user.getBirthDate());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getAuthority().equals("ROLE_CLIENT")));
    }
}
