package com.example.demo.controller;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartCookieService;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class CheckoutController {

    private final CartCookieService cartCookieService;
    private final OrderRepository orderRepository;

    public CheckoutController(CartCookieService cartCookieService, OrderRepository orderRepository) {
        this.cartCookieService = cartCookieService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/checkout")
    public String showCheckout(HttpServletRequest request, Model model) {
        model.addAttribute("cart", cartCookieService.load(request));
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String customerName,
                             @RequestParam String customerEmail,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        Cart cart = cartCookieService.load(request);

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCreatedAt(LocalDateTime.now());

        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet()) {
            Product product = cart.getProducts().get(entry.getKey());
            int quantity = entry.getValue();

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPriceAtPurchase(product.getPrice());

            order.addItem(item);
        }

        Order savedOrder = orderRepository.save(order);
        cart.clear();
        cartCookieService.save(response, cart); // empty cart -> deletes the cookie

        return "redirect:/order-confirmation/" + savedOrder.getId();
    }
}