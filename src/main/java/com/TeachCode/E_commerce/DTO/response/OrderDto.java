/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 */
public record OrderDto(
	    Long id,
	    Double totalPrice,
	    Double shippingCost,
	    String status,
	    LocalDateTime createdAt,
	    String fullName,
	    String phoneNumber,
	    String address,
	    String city,
	    String postalCode,
	    String country,
	    List<OrderItemDto> items,
	    List<OrderHistoryDto> histories
) {}
