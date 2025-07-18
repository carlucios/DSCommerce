package com.devsuperior.dscommerce.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class RoleTests {

    @Test
    public void testRoleFields() {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setAuthority("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setAuthority("ROLE_CLIENT");

        assertEquals(1L, role1.getId());
        assertEquals("ROLE_ADMIN", role1.getAuthority());
        assertEquals(2L, role2.getId());
        assertEquals("ROLE_CLIENT", role2.getAuthority());
    }
    
}
