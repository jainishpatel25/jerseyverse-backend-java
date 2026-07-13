package com.ecommerce.jerseyverse.mapper;

import com.ecommerce.jerseyverse.dto.response.cart.CartItemResponse;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;
import com.ecommerce.jerseyverse.entity.Cart;
import com.ecommerce.jerseyverse.entity.CartItem;
import com.ecommerce.jerseyverse.entity.Product;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import com.ecommerce.jerseyverse.enums.CartItemAvailabilityStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {

        List<CartItemResponse> itemResponses =
                cart.getCartItems()
                        .stream()
                        .map(this::toCartItemResponse)
                        .toList();

        BigDecimal subtotal =
                itemResponses.stream()
                        .map(CartItemResponse::getItemSubtotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal deliveryCharge = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;

        BigDecimal total = subtotal
                .subtract(discount)
                .add(deliveryCharge)
                .add(tax);

        CartResponse response = new CartResponse();

        response.setCartId(cart.getId());
        response.setItems(itemResponses);
        response.setSubtotal(subtotal);
        response.setDiscount(discount);
        response.setDeliveryCharge(deliveryCharge);
        response.setTax(tax);
        response.setTotal(total);

        return response;
    }

    public CartResponse toEmptyCartResponse() {

        CartResponse response = new CartResponse();

        response.setCartId(null);
        response.setItems(Collections.emptyList());
        response.setSubtotal(BigDecimal.ZERO);
        response.setDiscount(BigDecimal.ZERO);
        response.setDeliveryCharge(BigDecimal.ZERO);
        response.setTax(BigDecimal.ZERO);
        response.setTotal(BigDecimal.ZERO);

        return response;
    }

    private CartItemResponse toCartItemResponse(
            CartItem cartItem
    ) {

        ProductVariant productVariant =
                cartItem.getProductVariant();

        Product product =
                productVariant.getProduct();

        BigDecimal unitPrice =
                product.getPrice();

        BigDecimal itemSubtotal =
                unitPrice.multiply(
                        BigDecimal.valueOf(
                                cartItem.getQuantity()
                        )
                );

        CartItemAvailabilityStatus availabilityStatus =
                resolveAvailabilityStatus(
                        cartItem,
                        productVariant
                );

        CartItemResponse response =
                new CartItemResponse();

        response.setCartItemId(cartItem.getId());
        response.setProductId(product.getId());
        response.setProductName(product.getName());

        response.setImageUrl(product.getImageUrl());

        response.setSize(
                productVariant.getSize().toString()
        );

        response.setQuantity(cartItem.getQuantity());
        response.setUnitPrice(unitPrice);
        response.setItemSubtotal(itemSubtotal);
        response.setAvailabilityStatus(
                availabilityStatus
        );

        return response;
    }

    private CartItemAvailabilityStatus resolveAvailabilityStatus(
            CartItem cartItem,
            ProductVariant productVariant
    ) {

        if (cartItem.getQuantity()
                <= productVariant.getStock()) {

            return CartItemAvailabilityStatus.AVAILABLE;
        }

        return CartItemAvailabilityStatus.INSUFFICIENT_STOCK;
    }
}
