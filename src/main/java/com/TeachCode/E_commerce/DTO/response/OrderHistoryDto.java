/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import java.time.Instant;

/**
 * 
 */
public record OrderHistoryDto(
	    Long id,
	    String status,
	    Instant changedAt,
	    UserNameDto changedBy,
	    String comment
) {}
