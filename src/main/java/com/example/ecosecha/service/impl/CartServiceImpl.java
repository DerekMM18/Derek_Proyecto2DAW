package com.example.ecosecha.service.impl;
import com.example.ecosecha.model.Product;
import com.example.ecosecha.repository.ProductRepository;
import com.example.ecosecha.service.CartService;
import com.example.ecosecha.utlis.CarritoCompras;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
@SessionScope
@Transactional
public class CartServiceImpl implements CartService {
    private final CarritoCompras carritoCompras;
    private final ProductRepository productRepository;
    private final OrderDetailsServiceImpl orderDetailsServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    public CartServiceImpl(ProductRepository productRepository, OrderDetailsServiceImpl orderDetailsServiceImpl, ProductServiceImpl productServiceImpl) {
        this.productRepository = productRepository;
        this.orderDetailsServiceImpl = orderDetailsServiceImpl;
        this.productServiceImpl = productServiceImpl;
        carritoCompras = new CarritoCompras();
    }


    public void addProduct(long id) {
        if(productServiceImpl.get(id).isPresent()) {
            Product stockProduct = productServiceImpl.get(id).get();
            carritoCompras.addProduct(stockProduct);
        }
    }

    public boolean removeProduct(long id) {
        if(productServiceImpl.get(id).isPresent()) {
            Product stockProduct = productServiceImpl.get(id).get();
            carritoCompras.removeProduct(stockProduct);
            return true;
        }
        return false;
    }

    public CarritoCompras getCart() {
        return carritoCompras;
    }


    public long checkout() {
        long id = -1;
        List<Long> toRemove = new ArrayList<>();
        AtomicBoolean checkedOut = new AtomicBoolean(true);
        // updating stock with the database to check against cart
        if(carritoCompras.isEmpty()) {
            return id;
        }

        carritoCompras.getProducts().values().forEach(product -> {
            if(productServiceImpl.get(product.getId()).isPresent()) {
                if(productServiceImpl.get(product.getId()).get().getQuantity() < product.getQuantity()) {
                    checkedOut.set(false);
                    // remove product from cart and put back to sessionStock
                    toRemove.add(product.getId());
                    // update session stock
                }
            } else {
                checkedOut.set(false);
                toRemove.add(product.getId());
            }
        });
        // to avoid ConcurrentModificationException,
        // remove the products from shopping cart after iterating over it
        toRemove.forEach(productId -> carritoCompras.getProducts().remove(productId));
        if(checkedOut.get()) {
            carritoCompras.getProducts().values()
                .forEach(product ->
                    productServiceImpl.get(product.getId())
                        .ifPresent(product1 -> {
                            product1.setQuantity(product1.getQuantity() - product.getQuantity());
                            product1.setArchived(true);
                            productRepository.save(product1);
                        }));
            id = orderDetailsServiceImpl.save(new ArrayList<>(carritoCompras.getProducts().values()));
            carritoCompras.getProducts().clear();
            carritoCompras.setTotal(BigDecimal.ZERO);
        }
        return id;
    }


}
