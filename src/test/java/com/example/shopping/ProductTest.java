package com.example.shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000.0, 2);
    }

    // Test constructor validation
    @Test
    void shouldThrowExceptionWhenProductNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Product("", 1000.0, 2));
    }

    @Test
    void shouldThrowExceptionWhenPricePerUnitIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Laptop", 0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Product("Laptop", -10, 2));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Laptop", 1000.0, -1));
    }

}