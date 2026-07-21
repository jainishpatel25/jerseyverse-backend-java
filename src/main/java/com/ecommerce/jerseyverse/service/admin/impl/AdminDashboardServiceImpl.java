package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.response.dashboard.DashboardResponse;
import com.ecommerce.jerseyverse.repository.OrderRepository;
import com.ecommerce.jerseyverse.repository.ProductRepository;
import com.ecommerce.jerseyverse.service.admin.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public AdminDashboardServiceImpl(OrderRepository orderRepository,
                                ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public DashboardResponse getDashboardOverview() {
        Long totalOrders = orderRepository.count();

        Long totalProducts = productRepository.count();

        BigDecimal totalRevenue =
                orderRepository.getTotalRevenueByDeliveredOrders();

        DashboardResponse response = new DashboardResponse();
        response.setTotalOrders(totalOrders);
        response.setTotalProducts(totalProducts);
        response.setTotalRevenue(totalRevenue);

        return response;
    }
}
