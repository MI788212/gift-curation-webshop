package com.example.demo.favorites;

import com.example.demo.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@SessionScope
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

    public boolean containsProduct(Long productId) {
        return products.containsKey(productId);
    }
}