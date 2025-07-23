package com.devsuperior.dscommerce.factories;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Set;

public class UserFactory {

    public static User createUser(String email, String name, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("123456");
        user.setName(name);
        user.setBirthDate(LocalDate.of(2000, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC));
        user.setRoles(Set.of(role));
        return user;
    }

    public static User owner(Role roleUser) {
        return createUser("owner@example.com", "Order Owner", roleUser);
    }

    public static User stranger(Role roleUser) {
        return createUser("stranger@example.com", "Stranger User", roleUser);
    }

    public static User admin(Role roleAdmin) {
        return createUser("admin@example.com", "Admin User", roleAdmin);
    }

}
