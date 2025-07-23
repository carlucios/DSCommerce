package com.devsuperior.dscommerce.factories;

import com.devsuperior.dscommerce.entities.Role;

public class RoleFactory {

    public static Role createRole(String authority) {
        return Role.builder()
                .authority(authority)
                .build();
    }

    public static Role user() {
        return createRole("ROLE_CLIENT");
    }

    public static Role admin() {
        return createRole("ROLE_ADMIN");
    }

    public static Role operator() {
        return createRole("ROLE_OPERATOR");
    }

}
