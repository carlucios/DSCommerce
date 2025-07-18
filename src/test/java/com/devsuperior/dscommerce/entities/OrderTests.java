package com.devsuperior.dscommerce.entities;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class OrderTests {

    @Test
    public void testOrderFields() {
        Order order = new Order();
        order.setId(1L);
        order.setMoment(Instant.parse("2023-06-01T10:00:00Z"));
        order.setStatus(OrderStatus.PAID);
        order.setClient(null);

        assertEquals(1L, order.getId());
        assertEquals(OrderStatus.PAID, order.getStatus());
        assertEquals(Instant.parse("2023-06-01T10:00:00Z"), order.getMoment());
    }
}
