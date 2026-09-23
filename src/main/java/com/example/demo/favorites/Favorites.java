package com.example.demo.favorites;

import com.example.demo.model.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Favorites {

    private final Map<Long, Product> products = new LinkedHashMap<>();

    public void addItem(Product product) {
        products.put(product.getId(), product);
    }

    public void removeItem(Long productId) {
        products.remove(productId);
    }

    public Map<Long, Product> getProducts() {
        return products;
    }

    public boolean isFavoritesEmpty() {
        return products.isEmpty();
    }

    public void clear() {
        products.clear();
    }
}