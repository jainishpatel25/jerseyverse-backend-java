package com.ecommerce.jerseyverse.dto.response.order;

import com.ecommerce.jerseyverse.enums.OrderStatus;
import com.ecommerce.jerseyverse.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceSummaryResponse {

    private String invoiceNumber;
    private String orderNumber;
    private LocalDateTime invoiceDate;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private BigDecimal totalAmount;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
