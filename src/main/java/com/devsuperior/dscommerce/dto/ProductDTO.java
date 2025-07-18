package com.devsuperior.dscommerce.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.devsuperior.dscommerce.entities.Product;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;
    private String description;
    private Set<String> categories;

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.description = entity.getDescription();
        this.categories = entity.getCategories().stream().map(category -> category.getName()).collect(Collectors.toSet());
    }
    
}
