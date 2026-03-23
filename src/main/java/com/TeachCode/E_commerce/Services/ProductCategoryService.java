package com.TeachCode.E_commerce.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.TeachCode.E_commerce.DTO.request.ProductCategoryRequestDto;
import com.TeachCode.E_commerce.DTO.response.ProductCategoryResponseDto;
import com.TeachCode.E_commerce.DTO.response.ProductCategoryWithCount;
import com.TeachCode.E_commerce.Models.ProductCategory;

public interface ProductCategoryService {

	List<ProductCategory> getProductCategoryList();

	List<ProductCategoryWithCount> getProductCategoryListWithCount();

	ProductCategoryResponseDto createCategory(ProductCategoryRequestDto productCategoryRequestDto);

	ProductCategory updateCategory(Long id, ProductCategory productCategory);

	void deleteCategory(Long id);

	Optional<ProductCategory> getProductCategoryById(Long id);

}