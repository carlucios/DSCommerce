package com.devsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.devsuperior.dscommerce.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Instant birthDate;

    private Set<String> roles;

}
