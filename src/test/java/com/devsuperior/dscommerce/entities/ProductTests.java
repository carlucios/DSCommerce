package com.devsuperior.dscommerce.entities;

import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@DataJpaTest
public class ProductTests {

    @Test
    public void testProductFields() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setDescription("Latest model smartphone with advanced features");
        product.setPrice(999.99);
        product.setImgUrl("http://example.com/smartphone.jpg");
        Category category = Category.builder()
            .id(1L)
            .name("Electronics")
            .build();
        product.setCategories(Set.of(category));

        assertEquals(1L, product.getId());
        assertEquals("Smartphone", product.getName());
        assertEquals("Latest model smartphone with advanced features", product.getDescription());
        assertEquals(999.99, product.getPrice());
        assertEquals("http://example.com/smartphone.jpg", product.getImgUrl());
        assertEquals(1, product.getCategories().size());
        assertTrue(product.getCategories().stream().anyMatch(c -> c.getName().equals("Electronics")));
    }
    
}
