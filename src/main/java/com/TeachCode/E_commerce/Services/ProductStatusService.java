package com.TeachCode.E_commerce.Services;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.TeachCode.E_commerce.DTO.response.ProductStatusWithCount;
import com.TeachCode.E_commerce.Models.ProductStatus;

public interface ProductStatusService {

	List<ProductStatus> getProductStatusList();

	List<ProductStatusWithCount> getProductStatusListWithCount();

	ProductStatus saveProductStatus(ProductStatus productStatus);

}