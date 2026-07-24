package com.ecommerce.jerseyverse.util;

import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.OrderAddress;
import com.ecommerce.jerseyverse.entity.OrderItem;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class InvoicePdfGenerator {

    private static final String STORE_NAME = "JerseyVerse";
    private static final String STORE_ADDRESS =
            "Vapi, Gujarat, India - 396191";
    private static final String STORE_EMAIL =
            "jerseyverse@info.com";
    private static final String STORE_PHONE =
            "9409025116";

    public byte[] generate(Order order) {

        try (ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Document document = new Document(
                    PageSize.A4,
                    40,
                    40,
                    40,
                    40
            );

            PdfWriter.getInstance(document, outputStream);

            document.open();

            addHeader(document, order);
            addOrderInformation(document, order);
            addBillingInformation(document, order);
            addOrderItems(document, order);
            addPricingSummary(document, order);
            addFooter(document);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new IllegalStateException(
                    "Failed to generate invoice PDF.",
                    ex
            );
        }
    }

    private void addHeader(
            Document document,
            Order order
    ) throws DocumentException {

        Font titleFont = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                22
        );

        Font normalFont = FontFactory.getFont(
                FontFactory.HELVETICA,
                10
        );

        Paragraph title = new Paragraph(
                STORE_NAME.toUpperCase(),
                titleFont
        );

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        Paragraph businessInfo = new Paragraph(
                STORE_ADDRESS + "\n"
                        + STORE_EMAIL + "\n"
                        + STORE_PHONE,
                normalFont
        );

        businessInfo.setAlignment(Element.ALIGN_CENTER);
        businessInfo.setSpacingAfter(20);

        document.add(businessInfo);

        Paragraph invoiceTitle = new Paragraph(
                "INVOICE",
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        18
                )
        );

        invoiceTitle.setAlignment(Element.ALIGN_CENTER);
        invoiceTitle.setSpacingAfter(15);

        document.add(invoiceTitle);
    }

    private void addOrderInformation(
            Document document,
            Order order
    ) throws DocumentException {

        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(100);
        table.setSpacingAfter(20);

        addInfoRow(
                table,
                "Invoice Number",
                order.getInvoiceNumber()
        );

        addInfoRow(
                table,
                "Order Number",
                order.getOrderNumber()
        );

        addInfoRow(
                table,
                "Invoice Date",
                formatDate(order.getCreatedAt())
        );

        addInfoRow(
                table,
                "Order Status",
                order.getStatus().name()
        );

        addInfoRow(
                table,
                "Payment Method",
                order.getPaymentMethod().name()
        );

        addInfoRow(
                table,
                "Payment Status",
                order.getPaymentStatus().name()
        );

        document.add(table);
    }

    private void addInfoRow(
            PdfPTable table,
            String label,
            String value
    ) {

        Font labelFont = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                10
        );

        Font valueFont = FontFactory.getFont(
                FontFactory.HELVETICA,
                10
        );

        PdfPCell labelCell =
                new PdfPCell(new Phrase(label, labelFont));

        PdfPCell valueCell =
                new PdfPCell(new Phrase(value, valueFont));

        labelCell.setPadding(6);
        valueCell.setPadding(6);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addBillingInformation(
            Document document,
            Order order
    ) throws DocumentException {

        OrderAddress address = order.getOrderAddress();

        Paragraph heading = new Paragraph(
                "BILL TO",
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        12
                )
        );

        heading.setSpacingAfter(8);

        document.add(heading);

        StringBuilder billingAddress = new StringBuilder();

        billingAddress
                .append(address.getFullName())
                .append("\n");

        if (order.getUser() != null) {
            billingAddress
                    .append(order.getUser().getEmail())
                    .append("\n");
        }

        billingAddress
                .append(address.getPhoneNumber())
                .append("\n")
                .append(address.getAddressLine1())
                .append("\n");

        if (address.getAddressLine2() != null
                && !address.getAddressLine2().isBlank()) {

            billingAddress
                    .append(address.getAddressLine2())
                    .append("\n");
        }

        billingAddress
                .append(address.getCity())
                .append(", ")
                .append(address.getState())
                .append(" - ")
                .append(address.getPostalCode())
                .append("\n")
                .append(address.getCountry());

        Paragraph addressParagraph =
                new Paragraph(
                        billingAddress.toString(),
                        FontFactory.getFont(
                                FontFactory.HELVETICA,
                                10
                        )
                );

        addressParagraph.setSpacingAfter(20);

        document.add(addressParagraph);
    }

    private void addOrderItems(
            Document document,
            Order order
    ) throws DocumentException {

        Paragraph heading = new Paragraph(
                "ORDER ITEMS",
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        12
                )
        );

        heading.setSpacingAfter(8);

        document.add(heading);

        PdfPTable table = new PdfPTable(5);

        table.setWidthPercentage(100);
        table.setWidths(
                new float[]{3.5f, 1.2f, 1.2f, 1.8f, 1.8f}
        );

        addTableHeader(table, "Product");
        addTableHeader(table, "Size");
        addTableHeader(table, "Qty");
        addTableHeader(table, "Unit Price");
        addTableHeader(table, "Total");

        for (OrderItem item : order.getOrderItems()) {

            addTableCell(table, item.getProductName());
            addTableCell(table, item.getSize());

            addTableCell(
                    table,
                    String.valueOf(item.getQuantity())
            );

            addTableCell(
                    table,
                    formatAmount(item.getUnitPrice())
            );

            addTableCell(
                    table,
                    formatAmount(item.getSubtotal())
            );
        }

        table.setSpacingAfter(20);

        document.add(table);
    }

    private void addTableHeader(
            PdfPTable table,
            String text
    ) {

        Font font = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                9
        );

        PdfPCell cell =
                new PdfPCell(new Phrase(text, font));

        cell.setPadding(7);
        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(cell);
    }

    private void addTableCell(
            PdfPTable table,
            String text
    ) {

        PdfPCell cell = new PdfPCell(
                new Phrase(
                        text,
                        FontFactory.getFont(
                                FontFactory.HELVETICA,
                                9
                        )
                )
        );

        cell.setPadding(7);

        table.addCell(cell);
    }

    private void addPricingSummary(
            Document document,
            Order order
    ) throws DocumentException {

        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(45);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        addPriceRow(
                table,
                "Subtotal",
                formatAmount(order.getSubtotal()),
                false
        );

        if (order.getCouponCode() != null
                && !order.getCouponCode().isBlank()
                && order.getDiscountAmount() != null
                && order.getDiscountAmount()
                .compareTo(BigDecimal.ZERO) > 0) {

            addPriceRow(
                    table,
                    "Discount (" + order.getCouponCode() + ")",
                    "-" + formatAmount(order.getDiscountAmount()),
                    false
            );
        }

        addPriceRow(
                table,
                "Shipping",
                formatAmount(order.getShippingCharge()),
                false
        );

        /*
         * Tax calculation is not implemented in the current
         * Order model, so Phase 10 must not invent historical tax.
         */
        addPriceRow(
                table,
                "Tax",
                formatAmount(BigDecimal.ZERO),
                false
        );

        addPriceRow(
                table,
                "TOTAL",
                formatAmount(order.getTotalAmount()),
                true
        );

        document.add(table);
    }

    private void addPriceRow(
            PdfPTable table,
            String label,
            String amount,
            boolean bold
    ) {

        Font font = FontFactory.getFont(
                bold
                        ? FontFactory.HELVETICA_BOLD
                        : FontFactory.HELVETICA,
                10
        );

        PdfPCell labelCell =
                new PdfPCell(new Phrase(label, font));

        PdfPCell amountCell =
                new PdfPCell(new Phrase(amount, font));

        labelCell.setPadding(6);
        amountCell.setPadding(6);

        amountCell.setHorizontalAlignment(
                Element.ALIGN_RIGHT
        );

        table.addCell(labelCell);
        table.addCell(amountCell);
    }

    private String formatAmount(BigDecimal amount) {

        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        return "INR " + amount.setScale(
                2,
                RoundingMode.HALF_UP
        );
    }

    private String formatDate(LocalDateTime dateTime) {

        if (dateTime == null) {
            return "";
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy, hh:mm a"
                );

        return dateTime.format(formatter);
    }

    private void addFooter(
            Document document
    ) throws DocumentException {

        Paragraph footer = new Paragraph(
                "\nThank you for shopping with JerseyVerse.\n"
                        + STORE_EMAIL + " | "
                        + STORE_PHONE,
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        9
                )
        );

        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(25);

        document.add(footer);
    }
}
