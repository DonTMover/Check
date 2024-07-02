package ru.clevertec.check;

import java.math.BigDecimal;

public class OrderItem {
    private int productID;
    private int quantity;
    private double price;
    private String name;
    private boolean isWhosale;

    private OrderItem(int productID, int quantity, double price, String name) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.isWhosale = Product.getProductByID(getProductID()).isWholesaleProduct();
    }

    public void applyDiscount(BigDecimal discount) {
        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            // Convert price to BigDecimal for discount calculation
            BigDecimal discountedPrice = BigDecimal.valueOf(price)
                    .multiply(BigDecimal.ONE.subtract(discount));
            // Update price with the discounted value (converted back to double)
            this.price = discountedPrice.doubleValue();
        }
    }


    public static class Builder {
        private int productID;
        private int quantity;
        private double price;
        private String name;

        public Builder setProductID(int productID) {
            this.productID = productID;
            return this;
        }
        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public OrderItem build() {
            return new OrderItem(productID, quantity, price, name);
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
    public BigDecimal getDiscountPercentage() {
        // Return a defensive copy to prevent modification of the internal value
        return getDiscountPercentage() == null ? BigDecimal.ZERO : new BigDecimal(getDiscountPercentage().toString());
    }

    public boolean isWhosale() {
        return isWhosale;
    }
}
