package com.example.ecosecha.service;

import com.example.ecosecha.model.OrderDetails;
import com.example.ecosecha.model.Product;

import java.util.List;
import java.util.Map;

public interface OrderDetailsService {
    OrderDetails get(long id);
    List<OrderDetails> getUserOrders(String username);
    long save(List<Product> products);
    List<Product> populateProducts(Map<Long, Integer> cart);
}
