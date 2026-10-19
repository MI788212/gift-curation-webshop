package com.example.demo.cart;

import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Stores the cart in a browser cookie so it survives closing the browser
 * (and restarting the server) for up to a year.
 *
 * Cookie format: "productId_quantity" pairs joined by "-", e.g. "3_2-7_1".
 * Only ids and quantities are stored; names and prices are always read from
 * the database, so a customer can never change a price by editing the cookie.
 */
@Component
public class CartCookieService {

    private static final String COOKIE_NAME = "cart";
    private static final int MAX_AGE_SECONDS = 60 * 60 * 24 * 365; // 365 days
    private static final int MAX_QUANTITY = 99;

    private final ProductRepository productRepository;

    public CartCookieService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /** Builds a Cart from the cookie sent by the browser (empty cart if there is none). */
    public Cart load(HttpServletRequest request) {
        Cart cart = new Cart();
        if (request.getCookies() == null) return cart;

        for (Cookie cookie : request.getCookies()) {
            if (!COOKIE_NAME.equals(cookie.getName()) || cookie.getValue().isBlank()) continue;

            for (String part : cookie.getValue().split("-")) {
                String[] pair = part.split("_");
                if (pair.length != 2) continue;
                try {
                    long productId = Long.parseLong(pair[0]);
                    int quantity = Integer.parseInt(pair[1]);
                    if (quantity <= 0) continue;
                    int safeQuantity = Math.min(quantity, MAX_QUANTITY);
                    // Products that no longer exist in the database are silently dropped
                    productRepository.findById(productId)
                            .ifPresent(product -> cart.addItem(product, safeQuantity));
                } catch (NumberFormatException ignored) {
                    // Malformed part of the cookie - skip it
                }
            }
        }
        return cart;
    }

    /** Writes the cart back into the cookie. An empty cart deletes the cookie. */
    public void save(HttpServletResponse response, Cart cart) {
        String value = cart.getItems().entrySet().stream()
                .map(entry -> entry.getKey() + "_" + entry.getValue())
                .collect(Collectors.joining("-"));

        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cart.isCartEmpty() ? 0 : MAX_AGE_SECONDS);
        response.addCookie(cookie);
    }
}