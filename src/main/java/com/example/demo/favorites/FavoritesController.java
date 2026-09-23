package com.example.demo.favorites;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FavoritesController {

    private final Favorites favorites;
    private final ProductRepository productRepository;

    public FavoritesController(Favorites favorites, ProductRepository productRepository) {
        this.favorites = favorites;
        this.productRepository = productRepository;
    }

    @PostMapping("/favorites/add")
    @ResponseBody
    public ResponseEntity<Void> addToCart(@RequestParam Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        favorites.addItem(product);
        return ResponseEntity.noContent().build(); // 204, no body, no redirect
    }

    @GetMapping("/favorites")
    public String viewCart(Model model) {
        model.addAttribute("favorites", favorites);
        return "favorites";
    }

    @PostMapping("/favorites/remove")
    public String removeFromCart(@RequestParam Long productId) {
        favorites.removeItem(productId);
        return "redirect:/favorites";
    }
}