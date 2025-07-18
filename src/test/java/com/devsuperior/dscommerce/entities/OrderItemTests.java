package com.devsuperior.dscommerce.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class OrderItemTests {

    @Test
    public void testOrderItemFields() {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(2);
        orderItem.setPrice(10.0);
        orderItem.setProduct(null);
        orderItem.setOrder(null);

        assertEquals(2, orderItem.getQuantity());
        assertEquals(10.0, orderItem.getPrice());
        assertNull(orderItem.getProduct());
        assertNull(orderItem.getOrder());

    }
    
}
