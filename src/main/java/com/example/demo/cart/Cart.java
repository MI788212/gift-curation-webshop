package com.example.demo.cart;

import com.example.demo.model.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    // Product id -> quantity
    private final Map<Long, Integer> items = new LinkedHashMap<>();
    private final Map<Long, Product> products = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) {
        items.merge(product.getId(), quantity, Integer::sum);
        products.put(product.getId(), product);
    }

    public void removeItem(Long productId) {
        items.remove(productId);
        products.remove(productId);
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public Map<Long, Product> getProducts() {
        return products;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Product product = products.get(entry.getKey());
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            total = total.add(lineTotal);
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void setQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
        } else {
            items.put(productId, quantity);
        }
    }
}