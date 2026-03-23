/**
 * 
 */
package com.TeachCode.E_commerce.Repositories.custom;

import java.util.List;

import com.TeachCode.E_commerce.Models.Product;

/**
 * 
 */
public interface ProductRepositoryCustom {
	public List<Product> findRandomByCategory(Long categoryId, Long excludeId, int limit);
}
