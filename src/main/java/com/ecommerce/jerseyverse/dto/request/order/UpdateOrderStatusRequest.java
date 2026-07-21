package com.ecommerce.jerseyverse.dto.request.order;

import com.ecommerce.jerseyverse.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @NotNull(message = "Order status is required")
    private OrderStatus status;

}
