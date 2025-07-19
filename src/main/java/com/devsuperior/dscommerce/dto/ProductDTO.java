package com.devsuperior.dscommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;
    private String description;

    private Set<CategoryDTO> categories;

}
