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
    
    public void setDiscount(double discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Discount must be greater than zero");
        }

        if (discount > 1) {
            throw new IllegalArgumentException("Discount must be less than or equal to 1");
        }
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    
    public double calculateTotalCost() {
        return
                (products.stream().mapToDouble(Product::getTotalPrice).sum()) *
                        (1 - discount);
    }


}