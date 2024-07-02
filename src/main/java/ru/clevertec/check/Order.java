package ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int productID;
    private int quantity;
    private double price;
    private String name;
    private final List<OrderItem> orderItems; // Use final to prevent modification

    private Order(int productID, int quantity, double price, String name, List<OrderItem> orderItems) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.orderItems = orderItems;
    }

    public static class Builder {
        private int productID;
        private int quantity;
        private double price;
        private String name;
        private final List<OrderItem> orderItems = new ArrayList<>();

        public Builder productID(int productID) {
            this.productID = productID;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            // Create a defensive copy to avoid modifying the original list
            this.orderItems.addAll(new ArrayList<>(orderItems));
            return this;
        }

        public Builder addItem(OrderItem orderItem) {
            this.orderItems.add(orderItem);
            return this;
        }

        public Order build() {
            return new Order(productID, quantity, price, name, new ArrayList<>(orderItems)); // Defensive copy for orderItems
        }
    }

    public double getPrice() {
        return price;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public List<OrderItem> getOrderItems() {
        // Return a defensive copy to prevent modification of the internal list
        return new ArrayList<>(orderItems);
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        throw new UnsupportedOperationException("Order items cannot be modified after creation.");
    }

    @Override
    public String toString() {
        return "Order{" +
                "productID=" + productID +
                ", quantity=" + quantity +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }

    // New method to create a new Order.Builder with current object values
    public Order.Builder toBuilder() {
        return new Order.Builder()
                .productID(this.productID)
                .quantity(this.quantity)
                .price(this.price)
                .name(this.name)
                .orderItems(this.orderItems); // Pass a copy of orderItems
    }
}
