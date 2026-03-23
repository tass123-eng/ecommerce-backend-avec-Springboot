/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TeachCode.E_commerce.DTO.request.ProductDto;
import com.TeachCode.E_commerce.DTO.response.TopProductInWishlistDTO;
import com.TeachCode.E_commerce.Models.Product;
import com.TeachCode.E_commerce.Models.ProductCategory;
import com.TeachCode.E_commerce.Models.ProductStatus;
import com.TeachCode.E_commerce.Models.Role;
import com.TeachCode.E_commerce.Models.User;
import com.TeachCode.E_commerce.Repositories.ProductCategoryRepository;
import com.TeachCode.E_commerce.Repositories.ProductRepository;
import com.TeachCode.E_commerce.Repositories.ProductStatusRepository;
import com.TeachCode.E_commerce.Services.AuthenticationService;
import com.TeachCode.E_commerce.Services.ProductService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final AuthenticationService authenticationService;
	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final ProductStatusRepository productStatusRepository;

	@Override
	public Product createProduct(ProductDto productDTO) {
		Product product = new Product();
		return saveProduct(product, productDTO);
	}

	@Override
	public Product updateProduct(Long id, ProductDto productDTO) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec id: " + id));

		return saveProduct(existingProduct, productDTO);
	}

	private Product saveProduct(Product product, ProductDto productDTO) {
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setUnitPrice(productDTO.getUnitPrice());
		product.setUnitsInStock(productDTO.getUnitsInStock());
		product.setActive(productDTO.isActive());
		product.setLastUpdated(Instant.now());

		if (productDTO.getImageBase64() != null && productDTO.getImageContentType() != null) {
			product.setImageData(Base64.getDecoder().decode(productDTO.getImageBase64()));
			product.setImageContentType(productDTO.getImageContentType());
		}

		// Récupérer la catégorie
		ProductCategory category = productCategoryRepository.findById(productDTO.getCategoryId())
				.orElseThrow(() -> new EntityNotFoundException("Invalid category ID"));
		product.setCategory(category);

		// Récupérer le statut
		ProductStatus status = productStatusRepository.findById(productDTO.getProductStatusId())
				.orElseThrow(() -> new EntityNotFoundException("Invalid status ID"));
		product.setProductStatus(status);

		// Date de création si nouveau produit
		if (product.getDateCreated() == null) {
			product.setDateCreated(Instant.now());
		}

		return productRepository.save(product);
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public long getProductCount() {
		return productRepository.count();
	}

	@Override
	public Page<Product> getProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		User currentUser = null;
		try {
			currentUser = authenticationService.getCurrentUser();
		} catch (RuntimeException e) {
			// Pas authentifié -> currentUser reste null
		}

		if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
			// Admin -> tous les produits
			return productRepository.findAll(pageable);
		}

		// Utilisateur normal ou non authentifié -> seulement produits actifs
		return productRepository.findByActiveTrue(pageable);
	}

	@Override
	public Page<Product> getProductByStatusId(Long id, int page, int size) {
		return productRepository.findByProductStatusId(id, PageRequest.of(page, size));
	}

	@Override
	public List<Product> getProductByStatusIdLimit(Long id) {
		return productRepository.findByProductStatusIdLimit(id);
	}

	@Override
	public Page<Product> searchProductByName(String name, int page, int size) {
		return productRepository.findByNameContaining(name, PageRequest.of(page, size));
	}

	@Override
	public List<Product> getRandomProductByCategoryIdLimit(Long id) {
		return productRepository.findByCategoryIdLimit(id);
	}

	@Override
	public Page<Product> getProductsByCategory(Long categoryId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		User currentUser = null;
		try {
			currentUser = authenticationService.getCurrentUser();
		} catch (RuntimeException e) {
			// Pas authentifié
		}

		if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
			return productRepository.findByCategoryId(categoryId, pageable);
		}

		return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
	}

	@Override
	public long getProductCountByStatusName(String statusName) {
		return productRepository.countByProductStatus_StatusName(statusName);
	}

	@Override
	public List<Product> getSimilarProductsRandom(Long productId, int limit) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		return productRepository.findRandomByCategory(product.getCategory().getId(), productId, limit);
	}

	@Override
	public List<TopProductInWishlistDTO> getTopProductsInWishlist() {
		return productRepository.findTopProductsInWishlist(PageRequest.of(0, 5));
	}

	@Override
	public Page<Product> searchProducts(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword,
				pageable);
	}

	@Override
	public Page<Product> getAllProducts(int page, int size, String search, Long categoryId, Long statusId,
			Boolean active) {
		Pageable pageable = PageRequest.of(page, size);

		// Création d'une spécification vide pour construire dynamiquement les filtres
		Specification<Product> spec = Specification.where((Specification<Product>) null);

		if (search != null && !search.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"));
		}

		if (categoryId != null) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
		}

		if (statusId != null) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("productStatus").get("id"), statusId));
		}

		if (active != null) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("active"), active));
		}

		// Exécution de la requête avec la spécification et pagination
		return productRepository.findAll(spec, pageable);
	}

	@Transactional
	public void decreaseStock(Long productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Produit introuvable"));

		if (product.getUnitsInStock() < quantity) {
			throw new RuntimeException("Stock insuffisant pour le produit : " + product.getName());
		}

		product.setUnitsInStock(product.getUnitsInStock() - quantity);
		productRepository.save(product);
	}

	@Transactional
	public void increaseStock(Long productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Produit introuvable"));

		product.setUnitsInStock(product.getUnitsInStock() + quantity);
		productRepository.save(product);
	}
}
