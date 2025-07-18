package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;

import lombok.Data;

@Data
public class MinProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;
    
    public MinProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
    }
}
