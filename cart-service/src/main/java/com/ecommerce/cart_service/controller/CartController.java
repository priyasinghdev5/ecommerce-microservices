package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.model.Cart;
import com.ecommerce.cart_service.model.CartItem;
import com.ecommerce.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return service.getCart(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addToCart(@PathVariable String userId,
                          @RequestBody CartItem item) {
        return service.addToCart(userId, item);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public Cart removeFromCart(@PathVariable String userId,
                               @PathVariable String productId) {
        return service.removeFromCart(userId, productId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable String userId) {
        service.clearCart(userId);
        return "Cart cleared";
    }


    @DeleteMapping("/{userId}")
    public String deleteCart(@PathVariable String userId) {
        service.deleteCartByUserId(userId);
        return "Cart deleted";
    }
}
