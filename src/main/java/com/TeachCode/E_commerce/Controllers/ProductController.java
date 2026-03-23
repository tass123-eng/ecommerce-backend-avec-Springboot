/**
 * 
 */
package com.TeachCode.E_commerce.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TeachCode.E_commerce.DTO.request.ProductDto;
import com.TeachCode.E_commerce.Models.Product;
import com.TeachCode.E_commerce.Repositories.ProductCategoryRepository;
import com.TeachCode.E_commerce.Repositories.ProductStatusRepository;
import com.TeachCode.E_commerce.Services.OrderService;
import com.TeachCode.E_commerce.Services.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * 
 */
@Tag(name = "Produits", description = "Endpoints pour les produits")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductStatusRepository productStatusRepository;
    private final OrderService orderService;

    // Ajouter un produit
    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDTO) {
        try {
            Product createdProduct = productService.createProduct(productDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Modification d'un produit
    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDTO) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
