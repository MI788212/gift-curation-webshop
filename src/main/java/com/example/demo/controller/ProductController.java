package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", repo.findAll());
        return "products";
    }

    @GetMapping(value = "/products", params = "category")
    public String listByCategory(@RequestParam String category, Model model) {
        Category selected = Category.fromSlug(category).orElse(null);
        model.addAttribute("selectedCategory", selected);
        model.addAttribute("products", selected == null ? repo.findAll() : repo.findByCategory(selected));
        return "products";
    }

    @ModelAttribute("categories")
    public Category[] categories() {
        return Category.values();
    }

    // Lets products.html fill in the hearts of products that are already favourites
    @ModelAttribute("favoriteProductIds")
    public Set<Long> favoriteProductIds(@CookieValue(name = "favorites", defaultValue = "") String favorites) {
        return Arrays.stream(favorites.split("-"))
                .filter(id -> id.matches("\\d{1,18}"))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Проверуваме дали овој производ е во 'favorites' Cookie-то
        boolean isFavorite = false;
        if (request.getCookies() != null) {
            isFavorite = Arrays.stream(request.getCookies())
                    .filter(c -> "favorites".equals(c.getName()))
                    .findFirst()
                    .map(c -> Arrays.asList(c.getValue().split("-")).contains(String.valueOf(id)))
                    .orElse(false);
        }

        model.addAttribute("product", product);
        model.addAttribute("isFavorite", isFavorite);

        return "product-detail";
    }
}