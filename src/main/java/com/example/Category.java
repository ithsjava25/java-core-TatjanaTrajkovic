package com.example;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Category {
    private final String name;
    private static final Map<String, Category> CACHE = new ConcurrentHashMap<>();

    private Category(String name){
        this.name = Objects.requireNonNull(name, "name");
    }

    public static Category of(String name){
        if(name == null){
            throw new IllegalArgumentException("Category name can't be null");
        }
        String normalized = normalize(name);
        if(normalized.isBlank()){
            throw new IllegalArgumentException("Category name can't be blank");
        }
        return CACHE.computeIfAbsent(normalized, Category::new);
    }

    public static String normalize(String input){
        String trimmed = input.trim();
        String lower = trimmed.toLowerCase(Locale.ROOT);
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    public String name(){
        return name;
    }

}
