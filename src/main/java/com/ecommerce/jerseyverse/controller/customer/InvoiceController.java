package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.order.InvoiceSummaryResponse;
import com.ecommerce.jerseyverse.service.customer.InvoiceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<InvoiceSummaryResponse>> getInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        PageResponse<InvoiceSummaryResponse> response =
                invoiceService.getCustomerInvoices(page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping(
            value = "/{invoiceNumber}/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> getInvoicePdf(
            @PathVariable String invoiceNumber
    ) {

        byte[] pdf =
                invoiceService.generateInvoicePdf(invoiceNumber);

        String filename =
                "JerseyVerse-Invoice-" + invoiceNumber + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\""
                )
                .body(pdf);
    }
}
