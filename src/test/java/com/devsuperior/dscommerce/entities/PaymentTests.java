package com.devsuperior.dscommerce.entities;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class PaymentTests {

    @Test
    public void testPaymentFields() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setMoment(Instant.parse("2023-06-01T10:00:00Z"));
        payment.setOrder(null);

        assertEquals(1L, payment.getId());
        assertEquals(Instant.parse("2023-06-01T10:00:00Z"), payment.getMoment());
        assertNull(payment.getOrder());
    }
    
}
