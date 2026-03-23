/**
 * 
 */
package com.TeachCode.E_commerce.Services;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public interface StatisticsService {
    List<Long> getMonthlyRevenue(int year);
    Map<String, Long> getOrderStatusCounts(int year);
    List<Long> getMonthlyNewUsers(int year);
    List<Double> getMonthlySalesByProduct(Long productId, int year);
    Map<String, Object> getProductSalesSummary(Long productId);
}
