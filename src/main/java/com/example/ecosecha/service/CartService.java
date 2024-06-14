package com.example.ecosecha.service;

import com.example.ecosecha.utlis.CarritoCompras;

public interface CartService {
    void addProduct(long id);
    boolean removeProduct(long id);
    long checkout();
    CarritoCompras getCart();
}
