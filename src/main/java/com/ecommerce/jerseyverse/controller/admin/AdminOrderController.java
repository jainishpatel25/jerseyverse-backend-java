package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderSummaryResponse;
import com.ecommerce.jerseyverse.service.admin.AdminOrderService;
import com.ecommerce.jerseyverse.service.customer.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
