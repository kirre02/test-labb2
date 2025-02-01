package com.example.shopping;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Product> products = new ArrayList<>();
    private double discount;

    public Product get(String productName) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    public int size() {
        return products.size();
    }

    public void add(Product product) {
        products.add(product);
    }

    public void add(List<Product> products) {
        this.products.addAll(products);
    }

    public void remove(String productName) {
        products.removeIf(product -> product.getName().equalsIgnoreCase(productName));
    }
}