package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.model.CartItem;
import com.ecommerce.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    public Cart getCart(String userId) {
        return repository.findByUserId(userId)
                .orElseGet(() -> createEmptyCart(userId));
    }

    public Cart addToCart(String userId, CartItem item) {

        Cart cart = getCart(userId);

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(
                    existing.get().getQuantity() + item.getQuantity()
            );
        } else {
            cart.getItems().add(item);
        }

        return repository.save(cart);
    }

    public Cart removeFromCart(String userId, String productId) {

        Cart cart = getCart(userId);

        cart.getItems().removeIf(i -> i.getProductId().equals(productId));

        return repository.save(cart);
    }

    public void clearCart(String userId) {
        repository.findByUserId(userId)
                .ifPresent(repository::delete);
    }

    private Cart createEmptyCart(String userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return repository.save(cart);
    }

    public void deleteCartByUserId(String userId) {
        repository.deleteByUserId(userId);
    }
}