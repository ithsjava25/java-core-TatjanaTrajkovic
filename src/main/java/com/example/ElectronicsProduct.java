package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable{
    private final int warrantyMonths;
    private final BigDecimal weight; //kg

    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight){
        super(id, name, category, price);

        if(warrantyMonths < 0){
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }

        this.warrantyMonths = warrantyMonths;

        if(weight != null && weight.signum() < 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        this.weight = weight;
    }

    public int getWarrantyMonths(){
        return warrantyMonths;
    }

    // shippable
    @Override
    public BigDecimal calculateShippingCost(){
        BigDecimal base = BigDecimal.valueOf(79);
        if(weight == null){
            return base;
        }
        return weight.compareTo(BigDecimal.valueOf(5.0)) > 0
                ? base.add(BigDecimal.valueOf(49)) : base ;
    }

    @Override
    public Double weight() {
        return weight == null ? null : weight.doubleValue();
    }

    @Override
    public String productDetails(){
        return "Electronics: " + getName() + ", Warranty: " + warrantyMonths + " months";
    }

}
