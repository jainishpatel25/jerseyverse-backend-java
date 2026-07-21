package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.request.order.UpdateOrderStatusRequest;
import com.ecommerce.jerseyverse.dto.request.order.UpdatePaymentStatusRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderSummaryResponse;
import com.ecommerce.jerseyverse.service.admin.AdminOrderService;
import com.ecommerce.jerseyverse.service.customer.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService orderService;

    public AdminOrderController(AdminOrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public ResponseEntity<PageResponse<AdminOrderSummaryResponse>> getAllOrders(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        PageResponse<AdminOrderSummaryResponse> response =
                orderService.getAllOrders(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<AdminOrderDetailResponse> getOrderByIdForAdmin(
            @PathVariable Long orderId) {

        AdminOrderDetailResponse response =
                orderService.getOrderByIdForAdmin(orderId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<AdminOrderDetailResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        AdminOrderDetailResponse response =
                orderService.updateOrderStatus(orderId, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/payment-status")
    public ResponseEntity<AdminOrderDetailResponse> updatePaymentStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdatePaymentStatusRequest request) {

        AdminOrderDetailResponse response =
                orderService.updatePaymentStatus(orderId, request);

        return ResponseEntity.ok(response);
    }

}
