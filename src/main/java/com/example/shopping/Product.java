package com.example.shopping;

public class Product {
    private String name;
    private double pricePerUnit;
    private int quantity;
    private double discount;

    public Product(String name, double pricePerUnit, int quantity) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (pricePerUnit <= 0) {
            throw new IllegalArgumentException("Price per unit cannot be less than or equal to zero");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less then zero");
        }

        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        if (pricePerUnit <= 0) {
            throw new IllegalArgumentException("Price per unit cannot be less than or equal to zero");
        }
        this.pricePerUnit = pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }
        this.quantity = quantity;
    }


    public void setDiscount(double discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        this.discount = discount / 100;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalPrice() {
        return (pricePerUnit * quantity) * (1 - discount);
    }
}