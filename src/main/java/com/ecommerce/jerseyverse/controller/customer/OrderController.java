package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.request.order.PlaceOrderRequest;
import com.ecommerce.jerseyverse.dto.response.order.OrderDetailResponse;
import com.ecommerce.jerseyverse.service.customer.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDetailResponse> placeOrder(
            @Valid @RequestBody PlaceOrderRequest request) {

        OrderDetailResponse response = orderService.placeOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
