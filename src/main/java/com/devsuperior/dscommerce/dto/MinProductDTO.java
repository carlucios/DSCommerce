package com.devsuperior.dscommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MinProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;

}
