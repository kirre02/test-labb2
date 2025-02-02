package com.example.shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ShoppingCartTest {
    private ShoppingCart cart;
    private Product apple;
    private Product banana;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        apple = Mockito.mock(Product.class);
        banana = Mockito.mock(Product.class);

        Mockito.when(apple.getName()).thenReturn("Apple");
        Mockito.when(apple.getTotalPrice()).thenReturn(2.0);

        Mockito.when(banana.getName()).thenReturn("Banana");
        Mockito.when(banana.getTotalPrice()).thenReturn(1.5);
    }

    @Test
    void testAddProduct() {
        cart.add(apple);
        assertEquals(1, cart.size());
        assertEquals(apple, cart.get("Apple"));
    }

    @Test
    void testAddMultipleProducts() {
        List<Product> products = Arrays.asList(apple, banana);
        cart.add(products);
        assertEquals(2, cart.size());
    }

    @Test
    void testRemoveProduct() {
        cart.add(apple);
        cart.remove("Apple");
        assertNull(cart.get("Apple"));
        assertEquals(0, cart.size());
    }

    @Test
    void testSetValidDiscount() {
        cart.setDiscount(0.2);
        assertEquals(0.2, cart.getDiscount());
    }

    @Test
    void testSetInvalidDiscountNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cart.setDiscount(-0.1));
        assertEquals("Discount must be greater than zero", exception.getMessage());
    }

    @Test
    void testSetInvalidDiscountGreaterThanOne() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cart.setDiscount(1.5));
        assertEquals("Discount must be less than or equal to 1", exception.getMessage());
    }
}
