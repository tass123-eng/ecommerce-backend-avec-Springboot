/**
 * 
 */
package com.TeachCode.E_commerce.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.TeachCode.E_commerce.DTO.response.OrderDto;
import com.TeachCode.E_commerce.DTO.response.TopSellingProductDTO;
import com.TeachCode.E_commerce.Models.Order;
import com.TeachCode.E_commerce.Models.OrderItem;
import com.TeachCode.E_commerce.Models.OrderStatus;

/**
 * 
 */
public interface OrderService {
	List<String> getAllStatuses();

    Order createOrder(
        Set<OrderItem> items,
        Double totalPrice,
        Double shippingCost,
        String fullName,
        String phoneNumber,
        String address,
        String city,
        String postalCode,
        String country
    );

    Page<Order> getOrders(int page, int size);
    Page<Order> getOrdersByStatus(OrderStatus status, int page, int size);
    Double getTotalRevenue();
    List<TopSellingProductDTO> getTopSellingProducts();
    Optional<Order> getOrderById(Long id);
    OrderDto updateStatus(Long orderId, String newStatus);
    List<Map<String, Object>> getOrdersStats(LocalDateTime start, LocalDateTime end);
    Page<Order> getOrdersByProductId(Long productId, Pageable pageable);
    Page<Order> getOrdersForCurrentUser(int page, int size);
}
