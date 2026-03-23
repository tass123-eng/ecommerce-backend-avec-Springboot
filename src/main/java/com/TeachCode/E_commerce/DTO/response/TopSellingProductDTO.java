/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import com.TeachCode.E_commerce.Models.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 */
@Data
@AllArgsConstructor
public class TopSellingProductDTO {
    private Product product;
    private Long totalQuantity;
    private Double totalRevenue;
}
