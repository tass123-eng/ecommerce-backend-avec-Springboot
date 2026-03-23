/**
 * 
 */
package com.TeachCode.E_commerce.Mappers;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.TeachCode.E_commerce.DTO.response.OrderDto;
import com.TeachCode.E_commerce.DTO.response.OrderHistoryDto;
import com.TeachCode.E_commerce.DTO.response.OrderItemDto;
import com.TeachCode.E_commerce.DTO.response.UserNameDto;
import com.TeachCode.E_commerce.Models.Order;

/**
 * 
 */
@Component
public class OrderMapper {

    // readOnly = true pour les méthodes de lecture.
    @Transactional(readOnly = true)
    public OrderDto toResponse(Order order) {
        // Initialiser les collections pour éviter LazyInitializationException
        Hibernate.initialize(order.getHistories());
        Hibernate.initialize(order.getItems());

        List<OrderItemDto> items = order != null && order.getItems() != null ?
                order.getItems().stream()
                        .map(item -> new OrderItemDto(item.getProductId(),
                        		item.getProductName(),
                        		item.getPrice(),
                        		item.getQuantity()
                        		)) // Note: Vérifiez les arguments du constructeur ici
                        .toList() :
                List.of();

        List<OrderHistoryDto> histories = order != null && order.getHistories() != null ?
                order.getHistories().stream()
                        .map(h -> new OrderHistoryDto(
                                h.getId(),
                                h.getStatus().name(),
                                h.getChangedAt(),
                                h.getChangedBy() != null
                                ? new UserNameDto(h.getChangedBy().getFirstName(), h.getChangedBy().getLastName())
                                : new UserNameDto("System", ""),
                                h.getComment()
                        ))
                        .toList() :
                List.of();
        
        return new OrderDto(
                order.getId(),
                order.getTotalPrice(),
                order.getShippingCost(),
                order.getStatus().name(),
                order.getCreatedAt(),
                order.getFullName(),
                order.getPhoneNumber(),
                order.getAddress(),
                order.getCity(),
                order.getPostalCode(),
                order.getCountry(),
                items,
                histories
        );
    }
}