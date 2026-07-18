package com.ecommerce.jerseyverse.dto.request.order;

import com.ecommerce.jerseyverse.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public class PlaceOrderRequest {

    @NotNull(message = "Address is required")
    private Long addressId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}
