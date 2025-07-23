package com.devsuperior.dscommerce.factories;

import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

import java.util.Set;

public class ProductFactory {

    public static Product createProduct(Category category) {
        Product product = new Product();
        product.setName("Smartphone Realme C67");
        product.setPrice(1299.99);
        product.setDescription("Smartphone com câmera de 108MP, 8GB RAM");
        product.setImgUrl("https://example.com/images/realme.jpg");

        product.setCategories(Set.of(category));
        return product;
    }
}
