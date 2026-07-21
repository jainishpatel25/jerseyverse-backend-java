package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.request.order.UpdateOrderStatusRequest;
import com.ecommerce.jerseyverse.dto.request.order.UpdatePaymentStatusRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderSummaryResponse;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {

    PageResponse<AdminOrderSummaryResponse> getAllOrders(Pageable pageable);

    AdminOrderDetailResponse getOrderByIdForAdmin(Long orderId);

    AdminOrderDetailResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request);

    AdminOrderDetailResponse updatePaymentStatus(
            Long orderId,
            UpdatePaymentStatusRequest request);
}
