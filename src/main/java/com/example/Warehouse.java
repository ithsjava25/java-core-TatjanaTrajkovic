package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> INSTANCES = new ConcurrentHashMap<>();

    public static Warehouse getInstance(String name){
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
        products.put(p.uuid(), p);
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

    public List<Perishable> expiredProducts(){
        return products.values().stream()
                .filter(p -> (p instanceof Perishable per) && per.isExpired())
                .map(p -> (Perishable) p)
                .filter(Perishable::isExpired)
                .collect(Collectors.toList());
    }

    public List<Shippable> shippableProducts(){
        return products.values().stream()
                .filter(product -> (product instanceof Shippable))
                .map(product -> (Shippable) product)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String getName(){
        return name;
    }

    public void clearProducts(){
        products.clear();
        changedProductIds.clear();
    }

    public boolean isEmpty(){
        return products.isEmpty();
    }

    public void remove(UUID id){
        products.remove(id);
        changedProductIds.remove(id);
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return products.values()
                .stream()
                .collect(Collectors.groupingBy(Product::category));
    }

//    public Map<Category, List<Product>> getProductsGroupedByCategories(){
//        if(products.isEmpty()){
//            return Collections.emptyMap();
//        }
//
//        Map<Category, List<Product>> grouped = products.values().stream()
//                .collect(Collectors.groupingBy(
//                        Product::category,
//                        LinkedHashMap::new,
//                        Collectors.toList()
//                ));
//
//        Map <Category, List<Product>> immutable = new LinkedHashMap<>();
//        grouped.forEach((category, list) ->
//                immutable.put(category, Collections.unmodifiableList(new ArrayList<>(list))));
//        return Collections.unmodifiableMap(immutable);
//    }






}
