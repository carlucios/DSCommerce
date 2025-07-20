package com.devsuperior.dscommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PaymentDTO {

    private Long id;
    private Instant moment;

}
