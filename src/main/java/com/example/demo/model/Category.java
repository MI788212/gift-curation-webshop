package com.example.demo.model;

import java.util.Arrays;
import java.util.Optional;

public enum Category {
    GADGETS,
    ACCESSORIES,
    LIFESTYLE;

    public String slug() {
        return name().toLowerCase();
    }

    public static Optional<Category> fromSlug(String slug) {
        return Arrays.stream(values())
                .filter(category -> category.slug().equalsIgnoreCase(slug))
                .findFirst();
    }
}
