package ru.clevertec.check;

import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private double price;
    private boolean discount;
    private int quantityInStock;

    private Product(int id, String name, double price, boolean discount, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.quantityInStock = quantityInStock;
    }

    public static class Builder {
        private int id;
        private String name;
        private double price;
        private boolean discount;
        private int quantityInStock;

        public Builder(int id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder discount(boolean discount) {
            this.discount = discount;
            return this;
        }

        public Builder quantityInStock(int quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }

        public Product build() {
            if (name == null || price <= 0) {
                throw new IllegalArgumentException("Invalid product parameters");
            }
            return new Product(id, name, price, discount, quantityInStock);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isWholesaleProduct() {
        return discount;
    }

    public double getPriceWithDiscount() {
        return price * 0.97;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Double.compare(price, product.price) == 0 &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, discount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

}
