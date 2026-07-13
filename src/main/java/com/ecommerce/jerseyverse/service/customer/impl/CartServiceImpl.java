package com.ecommerce.jerseyverse.service.customer.impl;

import com.ecommerce.jerseyverse.dto.request.cart.AddCartItemRequest;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;
import com.ecommerce.jerseyverse.entity.Cart;
import com.ecommerce.jerseyverse.entity.CartItem;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.exception.InsufficientStockException;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.CartMapper;
import com.ecommerce.jerseyverse.repository.CartItemRepository;
import com.ecommerce.jerseyverse.repository.CartRepository;
import com.ecommerce.jerseyverse.repository.ProductVariantRepository;
import com.ecommerce.jerseyverse.repository.UserRepository;
import com.ecommerce.jerseyverse.security.utils.SecurityUtils;
import com.ecommerce.jerseyverse.service.customer.CartService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final SecurityUtils securityUtils;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            UserRepository userRepository,
            CartMapper cartMapper,
            SecurityUtils securityUtils, CartItemRepository cartItemRepository, ProductVariantRepository productVariantRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
        this.securityUtils = securityUtils;
        this.cartItemRepository = cartItemRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {

        User user = resolveCurrentUser();

        return cartRepository.findByUser(user)
                .map(cartMapper::toCartResponse)
                .orElseGet(cartMapper::toEmptyCartResponse);
    }

    private User resolveCurrentUser() {

        String email = securityUtils.getCurrentUserEmail();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }

    @Override
    @Transactional
    public CartResponse addItem(AddCartItemRequest request) {

        User user = resolveCurrentUser();

        ProductVariant productVariant =
                productVariantRepository
                        .findById(request.getProductVariantId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product variant not found"
                                )
                        );

        Cart cart = findOrCreateCart(user);

        Optional<CartItem> existingCartItem =
                cartItemRepository
                        .findByCartAndProductVariant(
                                cart,
                                productVariant
                        );

        if (existingCartItem.isPresent()) {

            CartItem cartItem = existingCartItem.get();

            int finalQuantity =
                    cartItem.getQuantity()
                            + request.getQuantity();

            validateStock(
                    productVariant,
                    finalQuantity
            );

            cartItem.setQuantity(finalQuantity);

            cartItemRepository.save(cartItem);

        } else {

            validateStock(
                    productVariant,
                    request.getQuantity()
            );

            CartItem cartItem = new CartItem(
                    cart,
                    productVariant,
                    request.getQuantity()
            );

            cartItemRepository.save(cartItem);

            cart.getCartItems().add(cartItem);
        }

        return cartMapper.toCartResponse(cart);
    }

    private Cart findOrCreateCart(User user) {

        return cartRepository.findByUser(user)
                .orElseGet(() -> {

                    Cart cart = new Cart(user);

                    return cartRepository.save(cart);
                });
    }

    private void validateStock(
            ProductVariant productVariant,
            int quantity
    ) {

        if (quantity > productVariant.getStock()) {

            throw new InsufficientStockException(
                    "Requested quantity exceeds available stock"
            );
        }
    }

}
