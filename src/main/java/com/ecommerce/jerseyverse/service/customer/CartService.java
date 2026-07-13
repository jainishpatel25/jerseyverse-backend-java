package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.request.cart.AddCartItemRequest;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;

public interface CartService {

    CartResponse getCart();

    CartResponse addItem(AddCartItemRequest request);

}
