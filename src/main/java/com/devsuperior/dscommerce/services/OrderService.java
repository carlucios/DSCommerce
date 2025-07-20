package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderStatus;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.mappers.OrderMapper;
import com.devsuperior.dscommerce.mappers.UserMapper;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order entity = orderRepository.findWithItemsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        System.out.println("Order Items: " + entity.getItems());

        OrderDTO dto = orderMapper.toDto(entity);
        System.out.println("OrderDTO Items: " + dto.getItems());

        return dto;
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        UserDTO userDTO = userService.authenticated();
        User client = userMapper.toEntity(userDTO);

        Order order = new Order();
        order.setClient(client);
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());
            order.getItems().add(item);
        }

        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
