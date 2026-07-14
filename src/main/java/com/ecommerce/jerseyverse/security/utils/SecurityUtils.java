package com.ecommerce.jerseyverse.security.utils;

import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.security.userdetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }

    public static String getCurrentUserEmail() {

        return getCurrentUser().getEmail();
    }

}