package com.example;

import java.time.LocalDate;

public interface Perishable { // interface är kontrakt för förbrukningsbara saker

    LocalDate expirationDate(); // Abstrakt metod: varje klass måste returnera sit utgångsdatum

    default boolean isExpired(){ // default-metod: true om idag är samma dag eller efter utgångsdatum, syfte är att avgöra om objektet har passerat sitt utgångsdatum
        LocalDate today = LocalDate.now(); // hämta dagens datum och lagra den i variabeln today
        return !today.isBefore(expirationDate()); // om dagens datum inte är före utgångsdatumet, idag är samma dag eller efter (utgånget) retur
    }





}
