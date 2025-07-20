package com.devsuperior.dscommerce.mappers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface OrderMapper {

    @Mapping(target = "items", ignore = true)
    public Order toEntity(OrderDTO dto);

    @Mapping(target = "items", expression = "java(mapItems(order.getItems()))")
    OrderDTO toDto(Order order);

    default Set<OrderItemDTO> mapItems(Set<OrderItem> items) {
        return items.stream().map(item -> new OrderItemDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getPrice(),
                item.getQuantity()
        )).collect(Collectors.toSet());
    }
}
