package com.devsuperior.dscommerce.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class CategoryTests {

    @Test
    public void testCategoryFields() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        assertEquals(1L, category.getId());
        assertEquals("Electronics", category.getName());
    }

}
