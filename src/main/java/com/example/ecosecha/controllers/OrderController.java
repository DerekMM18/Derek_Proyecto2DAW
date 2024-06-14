package com.example.ecosecha.controllers;

import com.example.ecosecha.model.OrderDetails;
import com.example.ecosecha.service.impl.CartServiceImpl;
import com.example.ecosecha.service.impl.OrderDetailsServiceImpl;
import com.example.ecosecha.utlis.CarritoCompras;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@SessionAttributes({"cart"})
public class OrderController {
    private final OrderDetailsServiceImpl orderDetailsServiceImpl;
    private final CartServiceImpl cartServiceImpl;

    public OrderController(OrderDetailsServiceImpl orderDetailsServiceImpl, CartServiceImpl cartServiceImpl) {
        this.orderDetailsServiceImpl = orderDetailsServiceImpl;
        this.cartServiceImpl = cartServiceImpl;
    }

    @ModelAttribute("cart")
    public CarritoCompras cart() {
        return cartServiceImpl.getCart();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderDetailsServiceImpl.getUserOrders(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "order-list";
    }

    @PreAuthorize("hasAuthority('ver:pedido')")
    @GetMapping("/{id}")
    public String get(@PathVariable("id") long id, Model model) {
        OrderDetails orderDetails = orderDetailsServiceImpl.get(id);
        model.addAttribute("products", orderDetailsServiceImpl.populateProducts(orderDetails.getProducts()));
        model.addAttribute("order", orderDetails);
        return "order";
    }
}
