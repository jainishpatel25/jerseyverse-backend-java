package com.ecommerce.jerseyverse.dto.response.order;

import com.ecommerce.jerseyverse.enums.OrderStatus;
import com.ecommerce.jerseyverse.enums.PaymentMethod;
import com.ecommerce.jerseyverse.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailResponse {

    private Long id;

    private String orderNumber;

    private OrderStatus status;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private BigDecimal subtotal;

    private BigDecimal shippingCharge;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private OrderAddressResponse orderAddressResponse;

    List<OrderItemResponse> orderItemResponseList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(BigDecimal shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderAddressResponse getOrderAddressResponse() {
        return orderAddressResponse;
    }

    public void setOrderAddressResponse(OrderAddressResponse orderAddressResponse) {
        this.orderAddressResponse = orderAddressResponse;
    }

    public List<OrderItemResponse> getOrderItemResponseList() {
        return orderItemResponseList;
    }

    public void setOrderItemResponseList(List<OrderItemResponse> orderItemResponseList) {
        this.orderItemResponseList = orderItemResponseList;
    }
}
