package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.order.UpdateOrderStatusRequest;
import com.ecommerce.jerseyverse.dto.request.order.UpdatePaymentStatusRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderSummaryResponse;
import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.OrderItem;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import com.ecommerce.jerseyverse.enums.OrderStatus;
import com.ecommerce.jerseyverse.enums.PaymentStatus;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.OrderMapper;
import com.ecommerce.jerseyverse.repository.OrderRepository;
import com.ecommerce.jerseyverse.service.admin.AdminOrderService;
import com.ecommerce.jerseyverse.util.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

     private final OrderRepository orderRepository;
     private final OrderMapper orderMapper;

    public AdminOrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public PageResponse<AdminOrderSummaryResponse> getAllOrders(Pageable pageable) {

        Page<AdminOrderSummaryResponse> orderPage = orderRepository
                .findAll(pageable)
                .map(orderMapper::toAdminOrderSummaryResponse);

        return PaginationUtils.buildPageResponse(orderPage);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminOrderDetailResponse getOrderByIdForAdmin(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found."));

        return orderMapper.toAdminOrderDetailResponse(order);
    }

    @Override
    @Transactional
    public AdminOrderDetailResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found."));

        validateOrderStatusTransition(
                order.getStatus(),
                request.getStatus()
        );

        if (request.getStatus() == OrderStatus.CANCELLED) {
            restoreStock(order);
        }

        order.setStatus(request.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toAdminOrderDetailResponse(updatedOrder);
    }



    private void validateOrderStatusTransition(
            OrderStatus currentStatus,
            OrderStatus newStatus) {

        if (currentStatus == newStatus) {
            throw new BadRequestException(
                    "Order is already in " + currentStatus + " status."
            );
        }

        switch (currentStatus) {

            case PENDING:

                if (newStatus != OrderStatus.CONFIRMED
                        && newStatus != OrderStatus.CANCELLED) {

                    throw new BadRequestException(
                            "A pending order can only be confirmed or cancelled."
                    );
                }

                break;

            case CONFIRMED:

                if (newStatus != OrderStatus.PROCESSING
                        && newStatus != OrderStatus.CANCELLED) {

                    throw new BadRequestException(
                            "A confirmed order can only move to processing or be cancelled."
                    );
                }

                break;

            case PROCESSING:

                if (newStatus != OrderStatus.SHIPPED) {

                    throw new BadRequestException(
                            "A processing order can only be shipped."
                    );
                }

                break;

            case SHIPPED:

                if (newStatus != OrderStatus.DELIVERED) {

                    throw new BadRequestException(
                            "A shipped order can only be delivered."
                    );
                }

                break;

            case DELIVERED:

                throw new BadRequestException(
                        "Delivered orders cannot be updated."
                );

            case CANCELLED:

                throw new BadRequestException(
                        "Cancelled orders cannot be updated."
                );

            default:

                throw new BadRequestException(
                        "Invalid order status transition."
                );
        }
    }

    @Override
    @Transactional
    public AdminOrderDetailResponse updatePaymentStatus(
            Long orderId,
            UpdatePaymentStatusRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found."));

        validatePaymentStatusTransition(
                order.getPaymentStatus(),
                request.getPaymentStatus()
        );

        order.setPaymentStatus(request.getPaymentStatus());

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toAdminOrderDetailResponse(updatedOrder);
    }

    private void validatePaymentStatusTransition(
            PaymentStatus currentStatus,
            PaymentStatus newStatus) {

        if (currentStatus == newStatus) {
            throw new BadRequestException(
                    "Payment is already in " + currentStatus + " status."
            );
        }

        switch (currentStatus) {

            case PENDING:

                if (newStatus != PaymentStatus.PAID
                        && newStatus != PaymentStatus.FAILED) {

                    throw new BadRequestException(
                            "A pending payment can only be marked as paid or failed."
                    );
                }

                break;

            case PAID:

                throw new BadRequestException(
                        "Paid payments cannot be updated."
                );

            case FAILED:

                throw new BadRequestException(
                        "Failed payments cannot be updated."
                );

            default:

                throw new BadRequestException(
                        "Invalid payment status transition."
                );
        }
    }

    private void restoreStock(Order order) {

        for (OrderItem orderItem : order.getOrderItems()) {

            ProductVariant variant =
                    orderItem.getProductVariant();

            variant.setStock(
                    variant.getStock() + orderItem.getQuantity()
            );
        }
    }
}
