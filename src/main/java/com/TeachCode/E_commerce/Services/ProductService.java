package com.TeachCode.E_commerce.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.TeachCode.E_commerce.DTO.request.ProductDto;
import com.TeachCode.E_commerce.DTO.response.TopProductInWishlistDTO;
import com.TeachCode.E_commerce.Models.Product;

public interface ProductService {

	Product createProduct(ProductDto productDTO);

	Product updateProduct(Long id, ProductDto productDTO);

	Optional<Product> getProductById(Long id);

	long getProductCount();

	Page<Product> getProducts(int page, int size);

	Page<Product> getProductByStatusId(Long id, int page, int size);

	List<Product> getProductByStatusIdLimit(Long id);

	Page<Product> searchProductByName(String name, int page, int size);

	List<Product> getRandomProductByCategoryIdLimit(Long id);

	Page<Product> getProductsByCategory(Long categoryId, int page, int size);

	long getProductCountByStatusName(String statusName);

	List<Product> getSimilarProductsRandom(Long productId, int limit);

	List<TopProductInWishlistDTO> getTopProductsInWishlist();

	Page<Product> searchProducts(String keyword, int page, int size);

	Page<Product> getAllProducts(int page, int size, String search, Long categoryId, Long statusId, Boolean active);

	void decreaseStock(Long productId, int quantity);

	void increaseStock(Long productId, int quantity);

}