package ru.clevertec.check;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class OrderItem {
    private static BigDecimal discountPercentage;
    private int productID;
    private int quantity;
    private double price;
    private String name;
    private boolean isWhosale;


    private OrderItem(int productID, int quantity, double price, String name) throws IOException {
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

        public OrderItem build() throws IOException {
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

    public boolean isWhosale() {
        return isWhosale;
    }

    public BigDecimal getDiscountPercentage(String DiscountCardNumber) {
        List<DiscountCard> discountCardList = CheckRunner.getDiscountCards();
        for (DiscountCard card : discountCardList) {
            if(card.getCardNumber().equals(DiscountCardNumber)) {
                discountPercentage = BigDecimal.valueOf(card.getDiscount());
            }
        }
        // Check if discount percentage is already set
        if (discountPercentage != null) {
            return discountPercentage; // Return cached value
        }

        // Calculate discount based on productID and wholesale rule
        BigDecimal discount = calculateDiscount(getProductID());

        // Apply default discount if necessary (replace with your logic)
        if (discount.compareTo(BigDecimal.ZERO) == 0) {
            discount = getDefaultDiscount(); // Apply default discount if no specific discount is calculated
        }

        discountPercentage = discount; // Cache the calculated value
        return discount;
    }

    private BigDecimal calculateDiscount(int productID) {
        // Implement logic to calculate discount based on productID,
        // considering factors like product type, promotions, etc.
        // This logic should determine if the wholesale discount applies (quantity >= 5)
        // and return the appropriate discount as a BigDecimal (e.g., 0.1 for 10% discount)

        // Example logic (replace with your actual implementation):
        if (isWhosale() && getQuantity() >= 5) {
            return BigDecimal.valueOf(0.1); // 10% wholesale discount
        } else {
            return BigDecimal.ZERO; // No specific discount for this item
        }
    }

    private BigDecimal getDefaultDiscount() {
        // Implement logic to determine the default discount to apply
        // This can be a fixed value or retrieved from a configuration
        // (e.g., return BigDecimal.ZERO for no default discount)
        return BigDecimal.ZERO; // Replace with your default discount logic
    }

}
