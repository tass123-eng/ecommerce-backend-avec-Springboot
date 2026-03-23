/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.TeachCode.E_commerce.Repositories.OrderRepository;
import com.TeachCode.E_commerce.Repositories.UserRepository;
import com.TeachCode.E_commerce.Services.StatisticsService;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	@Override
	public List<Long> getMonthlyRevenue(int year) {
		List<Tuple> tuples = orderRepository.getMonthlyRevenue(year);
		Long[] revenue = new Long[12];
		
		for (int i = 0; i < 12; i++) revenue[i] = 0L;

		for (Tuple t : tuples) {
			int month = ((Number) t.get("month")).intValue();
			double rev = ((Number) t.get("revenue")).doubleValue();
			revenue[month - 1] = (long) rev;
		}
		
		return List.of(revenue);
	}
	
	@Override
    public Map<String, Long> getOrderStatusCounts(int year) {
        List<Tuple> tuples = orderRepository.getOrderStatusCounts(year);
        Map<String, Long> result = new LinkedHashMap<>();
        
        for (Tuple t : tuples) {
            result.put(
                t.get("status").toString(), 
                ((Number) t.get("count")).longValue()
            );
        }
        
        return result;
    }
	
	@Override
    public List<Long> getMonthlyNewUsers(int year) {
        List<Tuple> tuples = userRepository.getMonthlyNewUsers(year);
        List<Long> usersPerMonth = new ArrayList<>(Collections.nCopies(12, 0L));
        
        for (Tuple t : tuples) {
            int month = ((Number) t.get("month")).intValue();
            long count = ((Number) t.get("count")).longValue();
            usersPerMonth.set(month - 1, count);
        }
        
        return usersPerMonth;
    }

    @Override
    public List<Double> getMonthlySalesByProduct(Long productId, int year) {
        List<Tuple> tuples = orderRepository.getMonthlySalesByProduct(year, productId);
        List<Double> salesPerMonth = new ArrayList<>(Collections.nCopies(12, 0.0));

        for (Tuple t : tuples) {
            int month = ((Number) t.get("month")).intValue();
            double total = ((Number) t.get("totalSales")).doubleValue();
            salesPerMonth.set(month - 1, total);
        }
        return salesPerMonth;
    }

    @Override
    public Map<String, Object> getProductSalesSummary(Long productId) {
        Tuple result = orderRepository.getProductSalesSummary(productId); //
        
        long totalQuantity = 0L;
        double totalSales = 0.0;

        if (result != null) {
            Number q = (Number) result.get("totalQuantity");
            Number r = (Number) result.get("totalSales");
            
            totalQuantity = (q != null) ? q.longValue() : 0L;
            totalSales = (r != null) ? r.doubleValue() : 0.0;
        }

        // Utilisation de Map.of pour retourner les données
        return Map.of(
            "totalQuantity", totalQuantity,
            "totalSales", totalSales
        );
    }
	
	
}
