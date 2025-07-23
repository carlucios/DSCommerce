package com.devsuperior.dscommerce.factories;

import com.devsuperior.dscommerce.entities.Category;

public class CategoryFactory {

    public static Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    public static Category electronics() {
        return createCategory("Eletronicos");
    }

    public static Category books() {
        return createCategory("Livros");
    }

    public static Category clothing() {
        return createCategory("Roupas");
    }
}
