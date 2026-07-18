package com.ecommerce.jerseyverse.service.customer.impl;

import com.ecommerce.jerseyverse.dto.request.order.PlaceOrderRequest;
import com.ecommerce.jerseyverse.dto.response.order.OrderDetailResponse;
import com.ecommerce.jerseyverse.entity.*;
import com.ecommerce.jerseyverse.enums.OrderStatus;
import com.ecommerce.jerseyverse.enums.PaymentMethod;
import com.ecommerce.jerseyverse.enums.PaymentStatus;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.OrderMapper;
import com.ecommerce.jerseyverse.repository.AddressRepository;
import com.ecommerce.jerseyverse.repository.CartRepository;
import com.ecommerce.jerseyverse.repository.OrderRepository;
import com.ecommerce.jerseyverse.repository.ProductVariantRepository;
import com.ecommerce.jerseyverse.security.utils.SecurityUtils;
import com.ecommerce.jerseyverse.service.customer.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderMapper orderMapper;
    private final SecurityUtils securityUtils;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            AddressRepository addressRepository,
            ProductVariantRepository productVariantRepository,
            OrderMapper orderMapper,
            SecurityUtils securityUtils) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.productVariantRepository = productVariantRepository;
        this.orderMapper = orderMapper;
        this.securityUtils = securityUtils;
    }

    @Transactional
    @Override
    public OrderDetailResponse placeOrder(PlaceOrderRequest request) {

        User user = securityUtils.getCurrentUser();

        Cart cart = getValidatedCart(user);

        Address address = getValidatedAddress(user, request.getAddressId());

        validatePaymentMethod(request.getPaymentMethod());

        validateStock(cart);

        String orderNumber = generateOrderNumber();

        Order order = createOrder(
                user,
                cart,
                address,
                orderNumber,
                request.getPaymentMethod()
        );

        Order savedOrder = orderRepository.save(order);

        reduceStock(cart);

        clearCart(cart);

        return orderMapper.toOrderDetailResponse(savedOrder);
    }

    private Cart getValidatedCart(User user) {

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found."));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new BadRequestException("Cannot place an order with an empty cart.");
        }

        return cart;
    }

    private Address getValidatedAddress(User user, Long addressId) {

        return addressRepository.findByIdAndUser(addressId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found."));
    }

    private void validatePaymentMethod(PaymentMethod paymentMethod) {

        if (paymentMethod != PaymentMethod.COD) {
            throw new BadRequestException("Only Cash on Delivery is currently supported.");
        }
    }

    private void validateStock(Cart cart) {

        for (CartItem cartItem : cart.getCartItems()) {

            ProductVariant variant = cartItem.getProductVariant();

            if (variant.getStock() < cartItem.getQuantity()) {
                throw new BadRequestException(
                        "Insufficient stock for "
                                + variant.getProduct().getName()
                                + " (Size: "
                                + variant.getSize()
                                + ").");
            }
        }
    }

    private String generateOrderNumber() {

        Optional<Order> latestOrder = orderRepository.findTopByOrderByIdDesc();

        if (latestOrder.isEmpty()) {
            return "SO00001";
        }

        String lastOrderNumber = latestOrder.get().getOrderNumber();

        int sequence = Integer.parseInt(lastOrderNumber.substring(2));

        sequence++;

        return String.format("SO%05d", sequence);
    }

    private Order createOrder(
            User user,
            Cart cart,
            Address address,
            String orderNumber,
            PaymentMethod paymentMethod) {

        Order order = new Order();

        order.setUser(user);
        order.setOrderNumber(orderNumber);

        order.setStatus(OrderStatus.PENDING);

        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(PaymentStatus.PENDING);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {

            BigDecimal itemSubtotal = cartItem.getProductVariant()
                    .getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            subtotal = subtotal.add(itemSubtotal);
        }

        BigDecimal shippingCharge = BigDecimal.ZERO;

        BigDecimal totalAmount = subtotal.add(shippingCharge);

        order.setSubtotal(subtotal);
        order.setShippingCharge(shippingCharge);
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems = createOrderItems(order, cart);

        OrderAddress orderAddress = createOrderAddress(order, address);

        order.setOrderItems(orderItems);
        order.setOrderAddress(orderAddress);

        return order;
    }

    private List<OrderItem> createOrderItems(
            Order order,
            Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            ProductVariant variant = cartItem.getProductVariant();

            Product product = variant.getProduct();

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProductVariant(variant);

            orderItem.setProductName(product.getName());

            orderItem.setSize(variant.getSize().name());

            orderItem.setUnitPrice(product.getPrice());

            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private OrderAddress createOrderAddress(
            Order order,
            Address address) {

        OrderAddress orderAddress = new OrderAddress();

        orderAddress.setOrder(order);

        orderAddress.setFullName(address.getFullName());
        orderAddress.setPhoneNumber(address.getPhoneNumber());

        orderAddress.setAddressLine1(address.getAddressLine1());
        orderAddress.setAddressLine2(address.getAddressLine2());

        orderAddress.setCity(address.getCity());
        orderAddress.setState(address.getState());
        orderAddress.setPostalCode(address.getPostalCode());
        orderAddress.setCountry(address.getCountry());

        return orderAddress;
    }

    private void reduceStock(Cart cart) {

        for (CartItem cartItem : cart.getCartItems()) {

            ProductVariant variant = cartItem.getProductVariant();

            variant.setStock(
                    variant.getStock() - cartItem.getQuantity()
            );
        }
    }


    private void clearCart(Cart cart) {

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }

}
