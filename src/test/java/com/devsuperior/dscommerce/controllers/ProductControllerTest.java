package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.factories.CategoryFactory;
import com.devsuperior.dscommerce.factories.ProductFactory;
import com.devsuperior.dscommerce.factories.RoleFactory;
import com.devsuperior.dscommerce.factories.UserFactory;
import com.devsuperior.dscommerce.repositories.CategoryRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.repositories.RoleRepository;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;

    private Category electronics;
    private Category books;
    private Product product;
    private Product edited_product;
    private Product existing_product;
    private Role roleUser;
    private Role roleAdmin;
    private User owner;
    private User stranger;
    private User admin;

    @BeforeAll
    @Transactional
    void setUpOnce() {
        roleUser = roleRepository.findByAuthority("ROLE_CLIENT").orElseGet(() ->
                roleRepository.save(RoleFactory.user())
        );

        roleAdmin = roleRepository.findByAuthority("ROLE_ADMIN").orElseGet(() ->
                roleRepository.save(RoleFactory.admin())
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

        electronics = categoryRepository.findByName("Eletronicos").orElseGet(() ->
                categoryRepository.save(CategoryFactory.electronics())
        );

        books = categoryRepository.findByName("Livros").orElseGet(() ->
                categoryRepository.save(CategoryFactory.books())
        );

        product = ProductFactory.createProduct(electronics);

        edited_product = ProductFactory.createProduct(books);

        existing_product = productRepository.findWithCategoriesByName("Smartphone Realme C67").orElseGet(() ->
                productRepository.save(ProductFactory.createProduct(electronics))
        );

    }

    @Test
    @Transactional
    void shouldAllowPublicAccessToProductListAndDetails() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void adminCanCreateUpdateAndDeleteProduct() throws Exception {
        long countBefore = productRepository.count();

        String response = mockMvc.perform(post("/products")
                        .with(user(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product createdProduct = objectMapper.readValue(response, Product.class);
        Long productId = createdProduct.getId();

        long countAfter = productRepository.count();
        assertEquals(countBefore + 1, countAfter);

        mockMvc.perform(put("/products/" + productId)
                        .with(user(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(edited_product)))
                .andExpect(jsonPath("$.categories[*].id", hasItem(books.getId().intValue())))
                .andExpect(status().isOk());

        countBefore = productRepository.count();
        mockMvc.perform(delete("/products/" + productId)
                        .with(user(admin)))
                .andExpect(status().isNoContent());
        countAfter = productRepository.count();
        assertEquals(countBefore - 1, countAfter);
    }

    @Test
    @Transactional
    void userCannotCreateOrModifyProducts() throws Exception {
        long countBefore = productRepository.count();

        mockMvc.perform(post("/products")
                        .with(user(stranger))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long countAfter = productRepository.count();
        assertEquals(countBefore, countAfter);

        long productId = existing_product.getId();

        mockMvc.perform(put("/products/" + productId)
                        .with(user(stranger))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isForbidden());

        countBefore = productRepository.count();

        mockMvc.perform(delete("/products/" + productId)
                        .with(user(stranger)))
                .andExpect(status().isForbidden());

        countAfter = productRepository.count();
        assertEquals(countBefore, countAfter);
    }
    @Test
    @Transactional
    void unauthenticatedUserCannotCreateOrModifyProducts() throws Exception {
        long countBefore = productRepository.count();

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long countAfter = productRepository.count();
        assertEquals(countBefore, countAfter);

        long productId = existing_product.getId();

        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isUnauthorized());

        countBefore = productRepository.count();

        mockMvc.perform(delete("/products/" + productId))
                .andExpect(status().isUnauthorized());

        countAfter = productRepository.count();
        assertEquals(countBefore, countAfter);
    }
}

