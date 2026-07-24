package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.InvoiceSummaryResponse;

public interface InvoiceService {

    PageResponse<InvoiceSummaryResponse> getCustomerInvoices(
            int page,
            int size
    );

}
