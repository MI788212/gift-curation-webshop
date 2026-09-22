package com.example.demo.cart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class CartConfig {

    @Bean
    @SessionScope
    public Cart cart() {
        return new Cart();
    }
}