/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

/**
 * 
 */
public record OrderItemDto(
	    Long productId,
	    String productName,
	    Double price,
	    int quantity
) {}
