package com.devsuperior.dscommerce.mappers;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(mapRolesToNames(user.getRoles()))")
    UserDTO toDto(User user);

    @Mapping(target = "roles", expression = "java(mapNamesToRoles(dto.getRoles()))")
    User toEntity(UserDTO dto);

    default Set<String> mapRolesToNames(Set<Role> roles) {
        return roles == null ? null :
                roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
    }

    default Set<Role> mapNamesToRoles(Set<String> names) {
        return names == null ? null :
                names.stream().map(name -> {
                    Role c = new Role();
                    c.setAuthority(name);
                    return c;
                }).collect(Collectors.toSet());
    }
}
