package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.request.order.PlaceOrderRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.OrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.OrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDetailResponse placeOrder(PlaceOrderRequest request);

    PageResponse<OrderSummaryResponse> getMyOrders(Pageable pageable);

    OrderDetailResponse getOrderById(Long orderId);

}
