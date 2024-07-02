package ru.clevertec.check;

public class Order {
    private int productID;
    private int quantity;
    private double price;
    private String name;

    private Order(int productID, int quantity, double price, String name) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }

    private class Builder {
        private int productID;
        private int quantity;
        private double price;
        private String name;

        public Builder(int productID, int quantity, double price, String name) {
            this.productID = productID;
            this.quantity = quantity;
            this.price = price;
            this.name = name;

        }

        public Order build() {
            return new Order(productID, quantity, price, name);
        }

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
}
