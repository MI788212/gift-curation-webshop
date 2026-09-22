package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    private final ProductRepository repo;

    public ProductController(ProductRepository repo) { this.repo = repo; }

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", repo.findAll());
        return "products"; // -> templates/products.html
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "product-detail";
    }
}