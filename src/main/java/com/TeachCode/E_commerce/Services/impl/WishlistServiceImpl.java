/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TeachCode.E_commerce.Models.Product;
import com.TeachCode.E_commerce.Models.User;
import com.TeachCode.E_commerce.Models.WishList;
import com.TeachCode.E_commerce.Repositories.WishListRepository;
import com.TeachCode.E_commerce.Services.AuthenticationService;
import com.TeachCode.E_commerce.Services.WishlistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishListRepository wishListRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Page<Product> getProductsForCurrentUser(int page, int size) {
        // Recupere l'utilisateur connecte
        User currentUser = authenticationService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);

        // Requete paginee
        return wishListRepository.findProductsByUserId(currentUser.getId(), pageable);
    }

    @Override
    @Transactional
    public void addProductToWishList(Product product) {
        // Recupere l'utilisateur connecte
        User currentUser = authenticationService.getCurrentUser();
        log.info("Product found for userId : {}", currentUser.getId());

        // Recuperer ou creer la wishlist de l'utilisateur
        WishList wishList = wishListRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> {
                    WishList w = new WishList();
                    w.setUserId(currentUser.getId());
                    return w;
                });

        // Ajouter le produit si pas deja present
        if (!wishList.getProducts().contains(product)) {
            wishList.getProducts().add(product);
            wishListRepository.save(wishList);
            log.info("Product added to wishlist successfully for userId: {}", currentUser.getId());
        } else {
            log.info("Product already in wishlist for userId: {}", currentUser.getId());
        }
    }

    @Override
    @Transactional
    public void removeProductFromWishList(Product product) {
        // Recupere l'utilisateur connecte
        User currentUser = authenticationService.getCurrentUser();
        
        // Recupere la wishlist de l'utilisateur
        WishList wishList = wishListRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Wishlist introuvable"));
        
        // Supprime le produit
        wishList.getProducts().remove(product);
        
        // Sauvegarde la wishlist mise a jour
        wishListRepository.save(wishList);
        log.info("Product removed from wishlist for userId: {}", currentUser.getId());
    }
}
