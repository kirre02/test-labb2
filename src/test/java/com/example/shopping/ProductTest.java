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

    // Test getter methods
    @Test
    void testGetName() {
        assertEquals("Laptop", product.getName());
    }

    @Test
    void testGetPricePerUnit() {
        assertEquals(1000.0, product.getPricePerUnit());
    }

    @Test
    void testGetQuantity() {
        assertEquals(2, product.getQuantity());
    }

    @Test
    void testGetDiscountInitiallyZero() {
        assertEquals(0.0, product.getDiscount());
    }

    // Test setter methods
    @Test
    void testSetName() {
        product.setName("Phone");
        assertEquals("Phone", product.getName());
    }

    @Test
    void testSetNameShouldThrowExceptionWhenEmpty() {
        assertThrows(IllegalArgumentException.class, () -> product.setName(""));
    }

    @Test
    void testSetPricePerUnit() {
        product.setPricePerUnit(1500.0);
        assertEquals(1500.0, product.getPricePerUnit());
    }

    @Test
    void testSetPricePerUnitShouldThrowExceptionWhenZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> product.setPricePerUnit(0));
        assertThrows(IllegalArgumentException.class, () -> product.setPricePerUnit(-50));
    }

    @Test
    void testSetQuantity() {
        product.setQuantity(5);
        assertEquals(5, product.getQuantity());
    }

    @Test
    void testSetQuantityShouldThrowExceptionWhenNegative() {
        assertThrows(IllegalArgumentException.class, () -> product.setQuantity(-3));
    }

    // Test discount handling
    @Test
    void testSetDiscount() {
        product.setDiscount(20);
        assertEquals(0.2, product.getDiscount());
    }

    @Test
    void testSetDiscountShouldThrowExceptionWhenNegativeOrAbove100() {
        assertThrows(IllegalArgumentException.class, () -> product.setDiscount(-10));
        assertThrows(IllegalArgumentException.class, () -> product.setDiscount(150));
    }

    // Test total price calculation
    @Test
    void testGetTotalPriceWithoutDiscount() {
        assertEquals(2000.0, product.getTotalPrice());
    }

    @Test
    void testGetTotalPriceWithDiscount() {
        product.setDiscount(10); // 10% discount
        assertEquals(1800.0, product.getTotalPrice());
    }

}