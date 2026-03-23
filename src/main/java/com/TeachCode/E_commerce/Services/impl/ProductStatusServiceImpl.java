/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.TeachCode.E_commerce.DTO.response.ProductStatusWithCount;
import com.TeachCode.E_commerce.Models.ProductStatus;
import com.TeachCode.E_commerce.Repositories.ProductStatusRepository;
import com.TeachCode.E_commerce.Services.ProductStatusService;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class ProductStatusServiceImpl implements ProductStatusService {

    private final ProductStatusRepository productStatusRepository;

    @Cacheable(cacheNames = "product-statuses")
    @Override
    public List<ProductStatus> getProductStatusList() {
        return productStatusRepository.findAll();
    }

    @Override
    public List<ProductStatusWithCount> getProductStatusListWithCount() {
        // Récupérer la liste des catégories avec le nombre de produits
        return productStatusRepository.findAllWithProductCount();
    }

    @CacheEvict(cacheNames = "product-statuses", allEntries = true)
    @Override
    public ProductStatus saveProductStatus(ProductStatus productStatus) {
        return productStatusRepository.save(productStatus);
    }
}
