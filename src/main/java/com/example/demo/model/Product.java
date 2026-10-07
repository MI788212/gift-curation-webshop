package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.ColumnDefault;

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
    @Enumerated(EnumType.STRING)
    private Category category;
    @ColumnDefault("false")
    private boolean featured;


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

    public Category getCategory() {return category;}

    public void setCategory(Category category) {this.category = category;}

    public boolean isFeatured() {return featured;}

    public void setFeatured(boolean featured) {this.featured = featured;}
}
