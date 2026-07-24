package com.ecommerce.jerseyverse.service.customer.impl;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.InvoiceSummaryResponse;
import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.mapper.InvoiceMapper;
import com.ecommerce.jerseyverse.repository.OrderRepository;
import com.ecommerce.jerseyverse.security.utils.SecurityUtils;
import com.ecommerce.jerseyverse.service.customer.InvoiceService;
import com.ecommerce.jerseyverse.util.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final OrderRepository orderRepository;
    private final InvoiceMapper invoiceMapper;
    private final SecurityUtils securityUtils;

    public InvoiceServiceImpl(
            OrderRepository orderRepository,
            InvoiceMapper invoiceMapper,
            SecurityUtils securityUtils
    ) {
        this.orderRepository = orderRepository;
        this.invoiceMapper = invoiceMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<InvoiceSummaryResponse> getCustomerInvoices(
            int page,
            int size
    ) {

        User user = securityUtils.getCurrentUser();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Order> orderPage =
                orderRepository.findByUserAndInvoiceNumberIsNotNull(
                        user,
                        pageable
                );

        Page<InvoiceSummaryResponse> responsePage =
                orderPage.map(invoiceMapper::toInvoiceSummaryResponse);

        return PaginationUtils.buildPageResponse(responsePage);
    }

}
