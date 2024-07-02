package ru.clevertec.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartCollector {

    private static List<Order> orders = new ArrayList<>();

    public static void collectCart(HashMap<Integer, Integer> purchases) {
        // Check for empty purchase map
        if (purchases.isEmpty()) {
            System.out.println("Error: No purchase data provided.");
            return;
        }

        // Create an empty order using the Builder pattern
        Order currentOrder = new Order.Builder().build();

        // Iterate through the purchases map
        for (Integer productId : purchases.keySet()) {
            int quantity = purchases.get(productId);

            // Find the product with the matching ID
            Product product = getProductById(productId);

            // If the product is found, add it to the order
            if (product != null) {
                OrderItem.Builder orderItemBuilder = new OrderItem.Builder()
                        .setProductID(product.getId())
                        .setQuantity(quantity)
                        .setPrice(product.getPrice())
                        .setName(product.getName());

                // Update the current order by adding the OrderItem
                currentOrder = currentOrder.toBuilder()
                        .addItem(orderItemBuilder.build())
                        .build();

            } else {
                System.out.println("Warning: Product with ID " + productId + " not found.");
            }
        }

        // Add the completed order to the list of orders
        orders.add(currentOrder);
    }

    private static Product getProductById(int productId) {
        System.out.println("Product retrieval logic not implemented yet.");
        return null;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static void clearOrders() {
        orders.clear();
    }
}
