package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

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