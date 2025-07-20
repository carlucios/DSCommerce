package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {
            "items",
            "items.id.product",
            "client",
            "payment",
            "client.roles"
    })
    Optional<Order> findWithItemsById(Long id);

}
