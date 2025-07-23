package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.factories.*;
import com.devsuperior.dscommerce.mappers.UserMapper;
import com.devsuperior.dscommerce.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderRepository orderRepository;

    private Role roleUser;
    private Role roleAdmin;
    private User owner;
    private User stranger;
    private User admin;
    private Category electronics;
    private Product product;
    private Order savedOrder;

    @BeforeAll
    @Transactional
    void setUpOnce() {
        roleUser = roleRepository.findByAuthority("ROLE_CLIENT").orElseGet(() ->
                roleRepository.save(RoleFactory.user())
        );

        roleAdmin = roleRepository.findByAuthority("ROLE_ADMIN").orElseGet(() ->
                roleRepository.save(RoleFactory.admin())
        );

        electronics = categoryRepository.findByName("Eletronicos").orElseGet(() ->
                categoryRepository.save(CategoryFactory.electronics())
        );

        product = productRepository.findWithCategoriesByName("Smartphone Realme C67").orElseGet(() ->
                productRepository.save(ProductFactory.createProduct(electronics))
        );

        owner = userRepository.findWithRolesByEmail("owner@gmail.com").orElseGet(() ->
                userRepository.save(UserFactory.createUser("owner@gmail.com", "Owner", roleUser))
        );

        stranger = userRepository.findWithRolesByEmail("stranger@gmail.com").orElseGet(() ->
                userRepository.save(UserFactory.createUser("stranger@gmail.com", "Stranger", roleUser))
        );

        admin = userRepository.findWithRolesByEmail("admin@gmail.com").orElseGet(() ->
                userRepository.save(UserFactory.createUser("admin@gmail.com", "Admin", roleAdmin))
        );

        savedOrder = orderRepository.save(OrderFactory.createOrder(owner, product));
    }

    @Test
    @Transactional
    void userCanCreateOrder() throws Exception {
        long countBefore = orderRepository.count();

        OrderItemDTO itemDTO = OrderItemDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(1)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .moment(Instant.now())
                .status(OrderStatus.WAITING_PAYMENT)
                .items(Set.of(itemDTO))
                .build();

        mockMvc.perform(post("/orders")
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim("username", "owner@gmail.com")
                                        .claim("sub", "owner@gmail.com")
                                )
                                .authorities(new SimpleGrantedAuthority("ROLE_CLIENT")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated());

        long countAfter = orderRepository.count();
        assertEquals(countBefore + 1, countAfter);
    }

    @Test
    @Transactional
    void unauthenticatedUserCannotCreateOrder() throws Exception {
        OrderItemDTO itemDTO = OrderItemDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(1)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .moment(Instant.now())
                .status(OrderStatus.WAITING_PAYMENT)
                .items(Set.of(itemDTO))
                .build();

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void orderOwnerCanAccessOrder() throws Exception {
        mockMvc.perform(get("/orders/" + savedOrder.getId())
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim("username", "owner@gmail.com")
                                        .claim("sub", "owner@gmail.com")
                                )
                                .authorities(new SimpleGrantedAuthority("ROLE_CLIENT"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void adminCanAccessOrder() throws Exception {
        mockMvc.perform(get("/orders/" + savedOrder.getId())
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim("username", "admin@gmail.com")
                                        .claim("sub", "admin@gmail.com")
                                )
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void otherUserCannotAccessOrder() throws Exception {
        mockMvc.perform(get("/orders/" + savedOrder.getId())
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim("username", "stranger@gmail.com")
                                        .claim("sub", "stranger@gmail.com")
                                )
                                .authorities(new SimpleGrantedAuthority("ROLE_CLIENT"))))
                .andExpect(status().isForbidden());
    }
}
