package com.example.demo.favorites;

import com.example.demo.favorites.Favorites;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class FavoritesConfig {

    @Bean
    @SessionScope
    public Favorites favorites() {
        return new Favorites();
    }
}