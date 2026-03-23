/**
 * 
 */
package com.TeachCode.E_commerce.DTO.request;

import java.util.Set;

import com.TeachCode.E_commerce.Models.OrderItem;

import lombok.Data;

/**
 * 
 */
@Data
public class CheckoutRequest {
    private Set<OrderItem> items;
    private Double totalPrice;
    private Double shippingCost;

    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
