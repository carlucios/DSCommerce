package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(value="/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) {
        OrderDTO newOrder = orderService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newOrder.getId()).toUri();
        return ResponseEntity.created(uri).body(newOrder);
    }

}
