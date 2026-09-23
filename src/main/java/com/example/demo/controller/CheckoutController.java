package com.example.demo.controller;

import com.example.demo.cart.Cart;
import com.example.demo.cart.CartCookieUtil;
import com.example.demo.cart.CartService;
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

    private final CartService cartService;
    private final OrderRepository orderRepository;

    public CheckoutController(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model, HttpServletRequest request, HttpServletResponse response) {
        String cartId = CartCookieUtil.getOrCreateCartId(request, response);
        model.addAttribute("cart", cartService.getCart(cartId));
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String customerName,
                             @RequestParam String customerEmail,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        String cartId = CartCookieUtil.getOrCreateCartId(request, response);
        Cart cart = cartService.getCart(cartId);

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
        cartService.clear(cartId);

        return "redirect:/order-confirmation/" + savedOrder.getId();
    }
}