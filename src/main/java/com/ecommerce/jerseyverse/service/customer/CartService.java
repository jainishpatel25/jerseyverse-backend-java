package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.request.cart.AddCartItemRequest;
import com.ecommerce.jerseyverse.dto.request.cart.UpdateCartItemQuantityRequest;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;

public interface CartService {

    CartResponse getCart();

    CartResponse addItem(AddCartItemRequest request);

    CartResponse updateItemQuantity(
            Long cartItemId,
            UpdateCartItemQuantityRequest request
    );

    CartResponse removeItem(Long cartItemId);

    CartResponse clearCart();

}
