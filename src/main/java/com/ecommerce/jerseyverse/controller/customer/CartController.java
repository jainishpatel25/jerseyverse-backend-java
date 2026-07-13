package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.request.cart.AddCartItemRequest;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;
import com.ecommerce.jerseyverse.service.customer.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {

        CartResponse response = cartService.getCart();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @Valid @RequestBody AddCartItemRequest request
    ) {

        CartResponse response =
                cartService.addItem(request);

        return ResponseEntity.ok(response);
    }
}
