package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    @Column(length = 1000)
    private String descriptionEn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getDescriptionEn() {return descriptionEn;}

    public void setDescriptionEn(String descriptionEn) {this.descriptionEn = descriptionEn;}

    public String descriptionFor(String language) {
        return "en".equals(language) && descriptionEn != null ? descriptionEn : description;
    }
}
