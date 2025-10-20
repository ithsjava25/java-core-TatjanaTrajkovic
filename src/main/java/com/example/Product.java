package com.example;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public abstract class Product {
    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    protected Product(UUID id, String name, Category category, BigDecimal price){
        this.id = Objects.requireNonNull(id, "id can't be null");
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("name can't be null or blank");
        }
        this.name = name;
        this.category = Objects.requireNonNull(category, "category can't be null");
        this.price = price;
    }

    public UUID getUuid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public abstract String productDetails();


}
