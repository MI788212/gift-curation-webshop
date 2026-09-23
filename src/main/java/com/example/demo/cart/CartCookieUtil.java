package com.example.demo.cart;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public class CartCookieUtil {

    private static final String CART_COOKIE_NAME = "cartId";
    private static final int CART_COOKIE_MAX_AGE = 60 * 60 * 24 * 365; // 365 days

    public static String getOrCreateCartId(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        String newCartId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(CART_COOKIE_NAME, newCartId);
        cookie.setMaxAge(CART_COOKIE_MAX_AGE);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return newCartId;
    }
}