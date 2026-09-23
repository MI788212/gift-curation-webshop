package com.example.demo.cart;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CartController {

    private static final String CART_COOKIE_NAME = "cartId";
    private static final int CART_COOKIE_MAX_AGE = 60 * 60 * 24 * 365; // 365 days

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity,
                            HttpServletRequest request, HttpServletResponse response) {
        cartService.addItem(CartCookieUtil.getOrCreateCartId(request, response), productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("cart", cartService.getCart(CartCookieUtil.getOrCreateCartId(request, response)));
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId,
                                 HttpServletRequest request, HttpServletResponse response) {
        cartService.removeItem(CartCookieUtil.getOrCreateCartId(request, response), productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity,
                                 HttpServletRequest request, HttpServletResponse response) {
        cartService.setQuantity(CartCookieUtil.getOrCreateCartId(request, response), productId, quantity);
        return "redirect:/cart";
    }
}