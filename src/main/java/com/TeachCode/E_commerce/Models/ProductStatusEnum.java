/**
 * 
 */
package com.TeachCode.E_commerce.Models;

/**
 * 
 */
public enum ProductStatusEnum {

    AVAILABLE("En stock"),
    OUT_OF_STOCK("En rupture de stock");

    private final String label;

    ProductStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
