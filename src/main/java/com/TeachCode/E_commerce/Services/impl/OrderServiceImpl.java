/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TeachCode.E_commerce.DTO.response.OrderDto;
import com.TeachCode.E_commerce.DTO.response.TopSellingProductDTO;
import com.TeachCode.E_commerce.Mappers.OrderMapper;
import com.TeachCode.E_commerce.Models.Order;
import com.TeachCode.E_commerce.Models.OrderHistory;
import com.TeachCode.E_commerce.Models.OrderItem;
import com.TeachCode.E_commerce.Models.OrderStatus;
import com.TeachCode.E_commerce.Models.User;
import com.TeachCode.E_commerce.Repositories.OrderHistoryRepository;
import com.TeachCode.E_commerce.Repositories.OrderRepository;
import com.TeachCode.E_commerce.Services.AuthenticationService;
import com.TeachCode.E_commerce.Services.OrderService;
import com.TeachCode.E_commerce.Services.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final AuthenticationService authenticationService;
	private final OrderHistoryRepository orderHistoryRepository;
	private final ProductService productService;
	private final OrderMapper orderMapper;

	@Cacheable(cacheNames = "order-statuses")
	@Override
	public List<String> getAllStatuses() {
		return Arrays.stream(OrderStatus.values()).map(Enum::name).toList();
	}

	@Override
	@Transactional
	public Order createOrder(Set<OrderItem> items, Double totalPrice, Double shippingCost, String fullName,
			String phoneNumber, String address, String city, String postalCode, String country) {

		// Récupération de l'utilisateur courant
		User currentUser = authenticationService.getCurrentUser();

		Order order = new Order();
		order.setUser(currentUser);
		order.setItems(items);
		order.setTotalPrice(totalPrice);
		order.setShippingCost(shippingCost);
		order.setStatus(OrderStatus.PENDING);
		order.setFullName(fullName);
		order.setPhoneNumber(phoneNumber);
		order.setAddress(address);
		order.setCity(city);
		order.setPostalCode(postalCode);
		order.setCountry(country);

		Order savedOrder = orderRepository.save(order);

		// Création de l'historique
		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setOrder(savedOrder);
		orderHistory.setStatus(OrderStatus.PENDING);
		orderHistory.setChangedBy(currentUser);
		orderHistory.setChangedAt(Instant.now());
		orderHistoryRepository.save(orderHistory);

		return savedOrder;
	}

	@Override
	public Page<Order> getOrders(int page, int size) {
		return orderRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Page<Order> getOrdersByStatus(OrderStatus status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		return orderRepository.findByStatus(status, pageable);
	}

	@Override
	public Double getTotalRevenue() {
		return orderRepository.getTotalRevenue();
	}

	@Override
	public Page<Order> getOrdersForCurrentUser(int page, int size) {
		// Récupère l'utilisateur connecté
		User currentUser = authenticationService.getCurrentUser();
		Pageable pageable = PageRequest.of(page, size);
		// Requête paginée
		return orderRepository.findOrdersByUserId(currentUser.getId(), pageable);
	}

	@Override
	public List<TopSellingProductDTO> getTopSellingProducts() {
		return orderRepository.findTopSellingProducts(PageRequest.of(0, 5));
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		return orderRepository.findById(id);
	}

	// Mise à jour du statut et gestion du stock
	@Override
	@Transactional
	public OrderDto updateStatus(Long orderId, String newStatus) {
		// 1. Récupération de la commande avec ses items et son historique
		Order order = orderRepository.findByIdWithItemsAndHistories(orderId)
				.orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'id : " + orderId));

		OrderStatus oldStatus = order.getStatus();
		OrderStatus statusEnum;

		// 2. Le bloc TRY pour valider la String reçue en paramètre
		try {
			statusEnum = OrderStatus.valueOf(newStatus.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Statut invalide : " + newStatus + ". Les valeurs possibles sont : "
					+ Arrays.toString(OrderStatus.values()));
		}

		
		if (oldStatus != statusEnum) {

			if (statusEnum == OrderStatus.DELIVERED && oldStatus != OrderStatus.DELIVERED) {
				order.getItems().forEach(item -> productService.decreaseStock(item.getProductId(), item.getQuantity()));
			}
			else if (oldStatus == OrderStatus.DELIVERED && statusEnum != OrderStatus.DELIVERED) {
				order.getItems().forEach(item -> productService.increaseStock(item.getProductId(), item.getQuantity()));
			}
		}

		// Historique de changement de statut
		User currentUser = authenticationService.getCurrentUser();
		OrderHistory history = OrderHistory.builder()
				.order(order)
				.status(statusEnum)
				.changedBy(currentUser)
				.changedAt(Instant.now())
				.build();
		orderHistoryRepository.save(history);

		//
		order.getHistories().add(history);

		order.setStatus(statusEnum);
		orderRepository.save(order);

		// 6. Retourne le DTO converti
		return orderMapper.toResponse(order);
	}
	
	@Override
    public List<Map<String, Object>> getOrdersStats(LocalDateTime start, LocalDateTime end) {
        return orderRepository.getOrdersStatsPerDate(start, end);
    }

    @Override
    public Page<Order> getOrdersByProductId(Long productId, Pageable pageable) {
        return orderRepository.findOrdersByProductId(productId, pageable);
    }

}
