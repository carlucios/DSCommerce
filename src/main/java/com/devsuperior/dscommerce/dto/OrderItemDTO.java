package com.devsuperior.dscommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDTO {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;

}
