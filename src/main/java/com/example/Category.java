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
        String trimmed = name.trim();
        if(trimmed.isEmpty()){
            throw new IllegalArgumentException("Category name can't be blank");
        }
        String normalized = normalize(trimmed);
        return CACHE.computeIfAbsent(normalized, Category::new);
    }

    public static String normalize(String input){
        String s = input.trim();
        if(s.isEmpty()){
            return "";
        }
        String lower = s.toLowerCase(Locale.ROOT);
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    public String name(){
        return name;
    }

}
