package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.request.order.PlaceOrderRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.OrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.OrderSummaryResponse;
import com.ecommerce.jerseyverse.service.customer.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<PageResponse<OrderSummaryResponse>> getMyOrders(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        PageResponse<OrderSummaryResponse> response = orderService.getMyOrders(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderById(
            @PathVariable Long orderId) {

        OrderDetailResponse response = orderService.getOrderById(orderId);

        return ResponseEntity.ok(response);
    }
}
