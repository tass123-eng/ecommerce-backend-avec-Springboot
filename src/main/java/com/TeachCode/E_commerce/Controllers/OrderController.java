/**
 * 
 */
package com.TeachCode.E_commerce.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TeachCode.E_commerce.DTO.request.CheckoutRequest;
import com.TeachCode.E_commerce.DTO.response.TopSellingProductDTO;
import com.TeachCode.E_commerce.Models.Order;
import com.TeachCode.E_commerce.Models.OrderStatus;
import com.TeachCode.E_commerce.Services.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * 
 */
@Tag(name = "Commandes", description = "Endpoints pour les commandes")
@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/checkout")
    public Order checkout(@RequestBody CheckoutRequest request) {
        return orderService.createOrder(
                request.getItems(),
                request.getTotalPrice(),
                request.getShippingCost(),
                request.getFullName(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getPostalCode(),
                request.getCountry()
        );
	}
	
	@GetMapping()
    public ResponseEntity<Page<Order>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) OrderStatus status
    ) {
        Page<Order> orders;
        if (status != null) {
            orders = orderService.getOrdersByStatus(status, page, size);
        } else {
            orders = orderService.getOrders(page, size);
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/total-revenue")
    public Double getTotalSales() {
        return orderService.getTotalRevenue();
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
    	Page<Order> ordersPage = orderService.getOrdersForCurrentUser(page, size);
        return ResponseEntity.ok(ordersPage);
    }

    @GetMapping("/top-selling-products")
    public List<TopSellingProductDTO> getTopSellingProducts() {
        return orderService.getTopSellingProducts();
    }

    @GetMapping("/stats")
    public List<Map<String, Object>> getStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        // Logique de gestion des dates par défaut
        LocalDateTime startDate = (start != null) 
                ? start.atStartOfDay() 
                : LocalDate.now().minusDays(7).atStartOfDay();

        LocalDateTime endDate = (end != null) 
                ? end.atTime(LocalTime.MAX) 
                : LocalDate.now().atTime(LocalTime.MAX);
        
        return orderService.getOrdersStats(startDate, endDate);
    }
}
