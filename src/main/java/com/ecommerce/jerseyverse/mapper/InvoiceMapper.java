package com.ecommerce.jerseyverse.mapper;

import com.ecommerce.jerseyverse.dto.response.order.InvoiceSummaryResponse;
import com.ecommerce.jerseyverse.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceSummaryResponse toInvoiceSummaryResponse(Order order) {

        InvoiceSummaryResponse response =
                new InvoiceSummaryResponse();

        response.setInvoiceNumber(order.getInvoiceNumber());
        response.setOrderNumber(order.getOrderNumber());
        response.setInvoiceDate(order.getCreatedAt());
        response.setOrderStatus(order.getStatus());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setTotalAmount(order.getTotalAmount());

        return response;
    }
}