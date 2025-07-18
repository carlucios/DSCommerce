package com.devsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.devsuperior.dscommerce.entities.User;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Instant birthDate;

    private Set<String> roles;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.birthDate = entity.getBirthDate();
        this.roles = entity.getRoles().stream().map(role -> role.getAuthority()).collect(Collectors.toSet());
    }
}
