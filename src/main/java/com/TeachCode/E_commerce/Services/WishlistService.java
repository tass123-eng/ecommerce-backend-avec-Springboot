package com.TeachCode.E_commerce.Services;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.TeachCode.E_commerce.Models.Product;

public interface WishlistService {

	Page<Product> getProductsForCurrentUser(int page, int size);

	void addProductToWishList(Product product);

	void removeProductFromWishList(Product product);

}