package com.devsuperior.dscommerce.factories;

import com.devsuperior.dscommerce.entities.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class OrderFactory {

    public static Order createOrder(User client, Product product) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(client);

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(1);
        item.setPrice(product.getPrice());

        order.setItems(new HashSet<>(Set.of(item)));

        return order;
    }
}
