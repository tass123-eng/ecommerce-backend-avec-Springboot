/**
 * 
 */
package com.TeachCode.E_commerce.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TeachCode.E_commerce.DTO.request.ProductCategoryRequestDto;
import com.TeachCode.E_commerce.DTO.response.ProductCategoryResponseDto;
import com.TeachCode.E_commerce.Models.ProductCategory;
import com.TeachCode.E_commerce.Services.ProductCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * 
 */
@Tag(name = "Catégories des produits", description = "Endpoints pour les catégories")
@RestController
@RequestMapping("/api/v1/product-categories")
@AllArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    // Récupérer toutes les catégories avec ou sans le compte des produits
    @GetMapping
    public List<?> getProductCategoryList(@RequestParam(name = "withCount", defaultValue = "false") boolean withCount) {
        if (withCount) {
            return productCategoryService.getProductCategoryListWithCount();
        } else {
            return productCategoryService.getProductCategoryList();
        }
    }

    // Création d'une nouvelle catégorie (ADMIN seulement)
 // Création d'une nouvelle catégorie (ADMIN uniquement)
    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ProductCategoryResponseDto createProductCategory(@RequestBody ProductCategoryRequestDto productCategory) {
        return productCategoryService.createCategory(productCategory);
    }

    // Mettre à jour le nom d'une catégorie
    @PutMapping("/{id}")
    public ProductCategory updateProductCategory(@PathVariable Long id, @RequestBody ProductCategory productCategory) {
        return productCategoryService.updateCategory(id, productCategory);
    }

    // Supprimer une catégorie (ADMIN uniquement)
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteCategory(id);
        return "Category with ID " + id + " has been deleted.";
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductCategory>> getProductCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productCategoryService.getProductCategoryById(id));
    }
}
