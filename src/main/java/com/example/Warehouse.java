package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private final Map<String, Warehouse> INSTANCES = new ConcurrentHashMap<>();

    public Warehouse getInstance(String name){
        Objects.requireNonNull(name, "name cannot be null");
        String key = name.trim();
        if(key.isEmpty()){
            throw new IllegalArgumentException("name cannot be blank or empty");
        }
        return INSTANCES.computeIfAbsent(key, Warehouse::new);
    }

    // instance state
    private final String name;
    private final Map<UUID, Product> products = new LinkedHashMap<>();
    private final Set<UUID> changedProductIds = new LinkedHashSet<>();

    private Warehouse(String name){
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public void addProduct(Product p){
        if(p == null){
            throw new IllegalArgumentException("Product cannot be null.");
        }
        products.put(p.getUuid(), p);
    }

    public List<Product> getProducts(){
        return List.copyOf(products.values());
    }

    public Optional<Product> getProductById(UUID id){
        return Optional.ofNullable(products.get(id));
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(newPrice, "newPrice");

        Product p = products.get(id);
        if (p == null)
            throw new NoSuchElementException("Product not found with id: " + id);

        if (p.price() == null || p.price().compareTo(newPrice) != 0) {
            p.price(newPrice);
            changedProductIds.add(id);
        }
    }

    public List<Product> getChangedProducts(){
        List<Product> list = changedProductIds.stream()
                .map(products::get)
                .filter(Objects::nonNull)
                .toList();
        return List.copyOf(list);
    }

    public List<Product> expiredProducts(){
        return products.values().stream()
                .filter(product -> (product instanceof Perishable per) && per.isExpired())
                .toList();
    }

    public List<Shippable> shippableProducts(){
        return products.values().stream()
                .filter(product -> (product instanceof Shippable))
                .map(product -> (Shippable) product)
                .toList();
    }

    public String getName(){
        return name;
    }


}
