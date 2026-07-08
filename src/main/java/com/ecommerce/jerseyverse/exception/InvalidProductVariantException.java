package com.ecommerce.jerseyverse.exception;

public class InvalidProductVariantException extends RuntimeException {
    public InvalidProductVariantException(String message) {
        super(message);
    }
}
