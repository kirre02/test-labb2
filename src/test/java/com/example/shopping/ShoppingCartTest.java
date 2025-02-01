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

}
