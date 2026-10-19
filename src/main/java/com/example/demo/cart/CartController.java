package com.example.demo.cart;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final CartCookieService cartCookieService;
    private final ProductRepository productRepository;

    public CartController(CartCookieService cartCookieService, ProductRepository productRepository) {
        this.cartCookieService = cartCookieService;
        this.productRepository = productRepository;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartCookieService.load(request);
        cart.addItem(product, quantity);
        cartCookieService.save(response, cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpServletRequest request, Model model) {
        model.addAttribute("cart", cartCookieService.load(request));
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Cart cart = cartCookieService.load(request);
        cart.removeItem(productId);
        cartCookieService.save(response, cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        Cart cart = cartCookieService.load(request);
        cart.setQuantity(productId, quantity);
        cartCookieService.save(response, cart);
        return "redirect:/cart";
    }
}