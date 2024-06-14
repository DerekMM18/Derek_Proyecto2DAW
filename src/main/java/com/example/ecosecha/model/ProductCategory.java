package com.example.ecosecha.model;

import java.util.ArrayList;
import java.util.List;

public enum ProductCategory {
    CARNE("carne"), LACTEOS("lácteos"), MARISCOS("mariscos"), PANADERIA("panadería"), APERITIVOS("aperitivos"), CARAMELOS("caramelos"), BEBIDAS("bebidas"), FIAMBRE("fiambre"), FRUTA("fruta"), VERDURAS("verduras");

    private final String category;

    ProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
    public static List<ProductCategory> getCategories() {
        List<ProductCategory> categories = new ArrayList<>();
        categories.add(PANADERIA);
        categories.add(BEBIDAS);
        categories.add(CARAMELOS);
        categories.add(LACTEOS);
        categories.add(FIAMBRE);
        categories.add(FRUTA);
        categories.add(CARNE);
        categories.add(MARISCOS);
        categories.add(APERITIVOS);
        categories.add(VERDURAS);
        return categories;
    }
}
