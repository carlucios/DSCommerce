package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findWithRolesByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado: " + username));
    }

    @Transactional(readOnly = true)
    public UserDTO authenticated() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
        String username = jwtPrincipal.getClaim("username");
        
        User user = repository.findWithRolesByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado: " + username));

        return mapper.toDto(user);
    }
    
}
