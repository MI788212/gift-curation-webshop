package com.example.demo.controller;

import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    private final ProductRepository repo;

    public ProductController(ProductRepository repo) { this.repo = repo; }

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", repo.findAll());
        return "products"; // -> templates/products.html
    }
}