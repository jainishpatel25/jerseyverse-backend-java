package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.order.UpdateOrderStatusRequest;
import com.ecommerce.jerseyverse.dto.request.order.UpdatePaymentStatusRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderDetailResponse;
import com.ecommerce.jerseyverse.dto.response.order.AdminOrderSummaryResponse;
import com.ecommerce.jerseyverse.entity.Order;
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

    final private OrderRepository orderRepository;
    final private OrderMapper orderMapper;

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
    public AdminOrderDetailResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        return null;
    }

    @Override
    public AdminOrderDetailResponse updatePaymentStatus(Long orderId, UpdatePaymentStatusRequest request) {
        return null;
    }
}
