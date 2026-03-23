/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.TeachCode.E_commerce.DTO.response.TopProductInWishlistDTO;
import com.TeachCode.E_commerce.Models.Product;
import com.TeachCode.E_commerce.Repositories.custom.ProductRepositoryCustom;

/**
 * 
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {
	
	Page<Product> findByProductStatusId(Long id, Pageable page);
	
	@Query("SELECT p FROM Product p WHERE p.productStatus.id = ?1 ORDER BY RAND() LIMIT 4")
	List<Product> findByProductStatusIdLimit(Long id);
	
	Page<Product> findByNameContaining(String name, Pageable page);
	
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 ORDER BY RAND() LIMIT 4")
	List<Product> findByCategoryIdLimit(Long id);
	
	long countByProductStatus_StatusName(String statusName);
	
	Page<Product> findByActiveTrue(Pageable page);
	
	Page<Product> findByCategoryId(Long categoryId, Pageable page);
	
	Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable page);
	
	@Query("""
			SELECT new com.TeachCode.E_commerce.DTO.response.TopProductInWishlistDTO (
				p, COUNT(w.id)
			)
			FROM WishList w
			JOIN w.products p
			GROUP BY
				p.id, p.name, p.description, p.unitPrice, p.active, p.unitsInStock, 
				p.dateCreated, p.lastUpdated, p.productStatus, p.category, 
				p.imageData, p.imageContentType
			ORDER BY COUNT(w.id) DESC
			""")
	List<TopProductInWishlistDTO> findTopProductsInWishlist(Pageable page);
	
	Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, 
			Pageable page);
}
