package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;

    private UserDTO client;

    private PaymentDTO payment;

    @NotEmpty(message = "Deve ter pelo menos um item")
    private Set<OrderItemDTO> items;
}
