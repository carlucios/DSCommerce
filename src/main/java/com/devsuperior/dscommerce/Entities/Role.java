package com.devsuperior.dscommerce.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String authority;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }
}
