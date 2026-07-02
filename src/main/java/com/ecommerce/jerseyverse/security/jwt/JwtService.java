package com.ecommerce.jerseyverse.security.jwt;

import com.ecommerce.jerseyverse.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractEmail(String token);

    boolean isTokenValid(String token, User user);

    boolean isTokenExpired(String token);
}
