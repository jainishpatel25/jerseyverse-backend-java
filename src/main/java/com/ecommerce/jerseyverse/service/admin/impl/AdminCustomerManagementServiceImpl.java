package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.response.CustomerListResponse;
import com.ecommerce.jerseyverse.dto.response.CustomerOrderSummaryResponse;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.Product.CustomerDetailsResponse;
import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.enums.Role;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.CustomerMapper;
import com.ecommerce.jerseyverse.repository.OrderRepository;
import com.ecommerce.jerseyverse.repository.UserRepository;
import com.ecommerce.jerseyverse.service.admin.AdminCustomerManagementService;
import com.ecommerce.jerseyverse.util.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminCustomerManagementServiceImpl implements AdminCustomerManagementService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final CustomerMapper customerMapper;

    public AdminCustomerManagementServiceImpl(UserRepository userRepository, OrderRepository orderRepository, CustomerMapper customerMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public PageResponse<CustomerListResponse> getCustomers(
            int page,
            int size,
            String search) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        String normalizedSearch =
                search == null ? "" : search.trim();

        Page<User> customerPage =
                userRepository.findByRoleAndSearch(
                        Role.ROLE_USER,
                        normalizedSearch,
                        pageable
                );

        List<Long> customerIds =
                customerPage.getContent()
                        .stream()
                        .map(User::getId)
                        .toList();

        Map<Long, Long> orderCountMap = new HashMap<>();

        if (!customerIds.isEmpty()) {

            List<Object[]> orderCounts =
                    orderRepository.countOrdersByUserIds(customerIds);

            for (Object[] row : orderCounts) {

                Long userId = (Long) row[0];
                Long count = (Long) row[1];

                orderCountMap.put(userId, count);
            }
        }

        Page<CustomerListResponse> responsePage =
                customerPage.map(user -> {

                    Long totalOrders =
                            orderCountMap.getOrDefault(
                                    user.getId(),
                                    0L
                            );

                    return customerMapper.toCustomerListResponse(
                            user,
                            totalOrders
                    );
                });

        return PaginationUtils.buildPageResponse(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDetailsResponse getCustomerDetails(Long customerId) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        )
                );

        // Make sure the requested user is actually a customer
        if (customer.getRole() != Role.ROLE_USER) {
            throw new ResourceNotFoundException(
                    "Customer not found with id: " + customerId
            );
        }

        List<Order> orders =
                orderRepository.findByUserIdOrderByCreatedAtDesc(customerId);

        List<CustomerOrderSummaryResponse> orderResponses =
                orders.stream()
                        .map(customerMapper::toCustomerOrderSummary)
                        .toList();

        Long totalOrders = (long) orders.size();

        return customerMapper.toCustomerDetailsResponse(
                customer,
                totalOrders,
                orderResponses
        );
    }
}
