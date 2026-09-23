package com.example.demo.cart;

import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public void addItem(String cartId, Long productId, int quantity) {
        cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + quantity);
                            cartItemRepository.save(item);
                        },
                        () -> cartItemRepository.save(new CartItem(cartId, productId, quantity))
                );
    }

    public void removeItem(String cartId, Long productId) {
        cartItemRepository.deleteByCartIdAndProductId(cartId, productId);
    }

    public void setQuantity(String cartId, Long productId, int quantity) {
        if (quantity <= 0) {
            removeItem(cartId, productId);
            return;
        }
        cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    cartItemRepository.save(item);
                });
    }

    public Cart getCart(String cartId) {
        Cart cart = new Cart();
        for (CartItem item : cartItemRepository.findByCartId(cartId)) {
            productRepository.findById(item.getProductId())
                    .ifPresent(product -> cart.addItem(product, item.getQuantity()));
        }
        return cart;
    }

    public void clear(String cartId) {
        cartItemRepository.findByCartId(cartId).forEach(cartItemRepository::delete);
    }
}