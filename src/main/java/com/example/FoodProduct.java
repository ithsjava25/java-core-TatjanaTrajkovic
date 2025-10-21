package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public final class FoodProduct extends Product implements Perishable, Shippable{
    private final LocalDate expirationDate;
    private final BigDecimal weight; //kg

    public FoodProduct(UUID id,
                       String name,
                       Category category,
                       BigDecimal price,
                       LocalDate expirationDate,
                       BigDecimal weight){

        if(price != null && price.signum() < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        super(id, name, category, price);

        this.expirationDate = Objects.requireNonNull(expirationDate, "expirationDate can't be null");

        if(weight != null && weight.signum() < 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        this.weight = weight;
    }

    //perishable
    @Override
    public LocalDate expirationDate(){
        return expirationDate;
    }

    //shipable
    @Override
    public BigDecimal calculateShippingCost(){
        if(weight == null){
            return BigDecimal.ZERO;
        }
        return weight.multiply(BigDecimal.valueOf(50));
    }
    @Override
    public Double weight(){
        return weight == null ? null : weight.doubleValue();
    }

    @Override
    public String productDetails(){
        return "Food: " + name() + ", Expires: " + expirationDate;
    }
}
