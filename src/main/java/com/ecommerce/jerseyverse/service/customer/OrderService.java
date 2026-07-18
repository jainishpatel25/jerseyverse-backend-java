package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.request.order.PlaceOrderRequest;
import com.ecommerce.jerseyverse.dto.response.order.OrderDetailResponse;

public interface OrderService {

    OrderDetailResponse placeOrder(PlaceOrderRequest request);

}
