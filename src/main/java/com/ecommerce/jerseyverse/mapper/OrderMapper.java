package com.ecommerce.jerseyverse.mapper;


import com.ecommerce.jerseyverse.dto.response.order.*;
import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.OrderAddress;
import com.ecommerce.jerseyverse.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderSummaryResponse toOrderSummaryResponse(Order order) {

        if (order == null) {
            return null;
        }

        OrderSummaryResponse response = new OrderSummaryResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setStatus(order.getStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());

        return response;
    }

    public OrderDetailResponse toOrderDetailResponse(Order order) {

        if (order == null) {
            return null;
        }

        OrderDetailResponse response = new OrderDetailResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setStatus(order.getStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());

        response.setSubtotal(order.getSubtotal());
        response.setShippingCharge(order.getShippingCharge());
        response.setTotalAmount(order.getTotalAmount());

        response.setCouponCode(order.getCouponCode());
        response.setDiscountAmount(order.getDiscountAmount());

        response.setCreatedAt(order.getCreatedAt());

        response.setOrderAddressResponse(
                toOrderAddressResponse(order.getOrderAddress())
        );

        response.setOrderItemResponseList(
                toOrderItemResponseList(order.getOrderItems())
        );

        return response;
    }

    public AdminOrderSummaryResponse toAdminOrderSummaryResponse(Order order) {

        if (order == null) {
            return null;
        }

        AdminOrderSummaryResponse response = new AdminOrderSummaryResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());

        response.setCustomerName(order.getUser().getName());
        response.setCustomerEmail(order.getUser().getEmail());

        response.setStatus(order.getStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());

        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());

        return response;
    }

    public AdminOrderDetailResponse toAdminOrderDetailResponse(Order order) {

        if (order == null) {
            return null;
        }

        AdminOrderDetailResponse response = new AdminOrderDetailResponse();

        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());

        response.setCustomerName(order.getUser().getName());
        response.setCustomerEmail(order.getUser().getEmail());

        response.setStatus(order.getStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());

        response.setSubtotal(order.getSubtotal());
        response.setShippingCharge(order.getShippingCharge());

        response.setCouponCode(order.getCouponCode());
        response.setDiscountAmount(order.getDiscountAmount());

        response.setTotalAmount(order.getTotalAmount());

        response.setCreatedAt(order.getCreatedAt());

        response.setOrderAddressResponse(
                toOrderAddressResponse(order.getOrderAddress())
        );

        response.setOrderItemResponseList(
                toOrderItemResponseList(order.getOrderItems())
        );

        return response;
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        OrderItemResponse response = new OrderItemResponse();

        response.setProductId(
                orderItem.getProductVariant().getProduct().getId()
        );

        response.setProductVariantId(
                orderItem.getProductVariant().getId()
        );

        response.setProductName(orderItem.getProductName());
        response.setSize(orderItem.getSize());
        response.setUnitPrice(orderItem.getUnitPrice());
        response.setQuantity(orderItem.getQuantity());
        response.setSubtotal(orderItem.getSubtotal());

        return response;
    }

    public OrderAddressResponse toOrderAddressResponse(OrderAddress address) {

        if (address == null) {
            return null;
        }

        OrderAddressResponse response = new OrderAddressResponse();

        response.setFullName(address.getFullName());
        response.setPhoneNumber(address.getPhoneNumber());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());

        return response;
    }

    private List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> items) {

        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());
    }

}