package com.example.demo.favorites;

import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class FavoritesController {

    private static final String COOKIE_NAME = "favorites";
    private static final int MAX_AGE_SECONDS = 60 * 60 * 24 * 365; // 365 days

    private final ProductRepository productRepository;

    public FavoritesController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Set<Long> readFavoriteIds(HttpServletRequest request) {
        if (request.getCookies() == null) return new LinkedHashSet<>();
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(COOKIE_NAME))
                .findFirst()
                .map(c -> c.getValue().isBlank()
                        ? new LinkedHashSet<Long>()
                        : Arrays.stream(c.getValue().split("-"))
                        .map(Long::parseLong)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .orElse(new LinkedHashSet<>());
    }

    private void writeFavoriteIds(HttpServletResponse response, Set<Long> ids) {
        String value = ids.stream().map(String::valueOf).collect(Collectors.joining("-"));
        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setMaxAge(MAX_AGE_SECONDS);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @PostMapping("/favorites/add")
    @ResponseBody
    public ResponseEntity<Void> addToCart(@RequestParam Long productId,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Set<Long> ids = readFavoriteIds(request);
        ids.add(productId);
        writeFavoriteIds(response, ids);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorites/remove")
    public String removeFromCart(@RequestParam Long productId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Set<Long> ids = readFavoriteIds(request);
        ids.remove(productId);
        writeFavoriteIds(response, ids);
        return "redirect:/favorites";
    }

    @GetMapping("/favorites")
    public String viewCart(HttpServletRequest request, Model model) {
        Set<Long> ids = readFavoriteIds(request);

        Favorites favorites = new Favorites();
        for (Long id : ids) {
            productRepository.findById(id).ifPresent(favorites::addItem);
        }

        model.addAttribute("favorites", favorites);
        return "favorites";
    }
}