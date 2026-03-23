/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.TeachCode.E_commerce.DTO.request.ProductCategoryRequestDto;
import com.TeachCode.E_commerce.DTO.response.ProductCategoryResponseDto;
import com.TeachCode.E_commerce.DTO.response.ProductCategoryWithCount;
import com.TeachCode.E_commerce.Models.ProductCategory;
import com.TeachCode.E_commerce.Repositories.ProductCategoryRepository;
import com.TeachCode.E_commerce.Services.ProductCategoryService;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Cacheable(cacheNames = "categories")
    @Override
    public List<ProductCategory> getProductCategoryList() {
        // Récupérer la liste des catégories
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategoryWithCount> getProductCategoryListWithCount() {
        // Récupérer la liste des catégories avec le nombre de produits par
        return productCategoryRepository.findAllWithProductCount();
    }

    @CacheEvict(cacheNames = "categories", allEntries = true) // Vider toute la cache "categories"
    @Override
    public ProductCategoryResponseDto createCategory(ProductCategoryRequestDto productCategoryRequestDto) {

        // Vérifier si le nom existe déjà
        productCategoryRepository.findByCategoryName(productCategoryRequestDto.getCategoryName())
                .ifPresent(existing -> {
                    throw new IllegalStateException("Category name already exists");
                });

        ProductCategory category = ProductCategory.builder()
                .categoryName(productCategoryRequestDto.getCategoryName())
                .build();

        ProductCategory saved = productCategoryRepository.save(category);

        return ProductCategoryResponseDto.builder()
                .id(saved.getId())
                .categoryName(saved.getCategoryName())
                .build();
    }
    
    @CacheEvict(cacheNames = "categories", allEntries = true) // Vider toute la cache "categories"
    @Override
    public ProductCategory updateCategory(Long id, ProductCategory productCategory) {
        // Récupérer la catégorie depuis l'id, si ça existe ça retourne un objet ProductCategory sinon null
        Optional<ProductCategory> existingCategory = productCategoryRepository.findById(id);
        // Vérification s'il existe une catégorie avec l'id envoyé
        if (existingCategory.isPresent()) {
            // Extraction de l'entité depuis la catégorie trouvée
            ProductCategory categoryToUpdate = existingCategory.get();
            // Mettre à jour le nom de la catégorie
            categoryToUpdate.setCategoryName(productCategory.getCategoryName());
            // Sauvegarder la catégorie
            return productCategoryRepository.save(categoryToUpdate);
        }
        // Dans le cas où il n'y a pas de catégorie avec l'id envoyé en paramètre, on retourne une exception
        throw new RuntimeException("Category not found with id: " + id);
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    @Override
    public void deleteCategory(Long id) {
        // Récupérer la catégorie depuis l'id, si ça existe ça retourne un objet ProductCategory
        Optional<ProductCategory> existingCategory = productCategoryRepository.findById(id);

        // Vérification s'il existe une catégorie avec l'id envoyé
        if (existingCategory.isPresent()) {
            // Supprimer la catégorie trouvée
            productCategoryRepository.delete(existingCategory.get());
        } else {
            // Dans le cas où il n'y a pas de catégorie avec l'id envoyé en paramètre on revoie 
            throw new RuntimeException("Category not found with id: " + id);
        }
    }
    
    @Override
    public Optional<ProductCategory> getProductCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }
}
