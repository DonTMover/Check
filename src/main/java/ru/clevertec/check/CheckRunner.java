package ru.clevertec.check;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckRunner {
    protected static final String PRODUCTS_FILE = "./src/main/resources/products.csv";
    protected static final String DISCOUNT_CARDS_FILE = "./src/main/resources/discountCards.csv";
    protected static final String RESULT_FILE = "./result.csv";

    private static List<Product> products;
    private static Map<Integer, Integer> purchases;
    private static Integer discountCardId;
    private static BigDecimal balanceDebitCard;

    public static void main(String[] args) throws IOException {
        // Parse products CSV file
        products = ParseProductsCSV.parseProductsCSV(PRODUCTS_FILE);

        // Process command-line arguments
        parseArguments(args);

        // Check if purchase data is available
        if (purchases.isEmpty()) {
            System.err.println("Error: No purchase data found.");
            return;
        }

        // Calculate total cost with discounts
        Order order = calculateTotalCostWithDiscounts();

        // Check balance and write order to file
        checkBalanceAndWriteOrder(order);
    }

    private static void parseArguments(String[] args) {
        purchases = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                discountCardId = Integer.parseInt(arg.split("=")[1]);
            } else if (arg.startsWith("balanceDebitCard=")) {
                balanceDebitCard = new BigDecimal(arg.split("=")[1]);
            } else {
                try {
                    String[] parts = arg.split("-");
                    int productId = Integer.parseInt(parts[0]);
                    int quantity = Integer.parseInt(parts[1]);
                    purchases.put(productId, quantity);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing argument: " + e.getMessage());
                }
            }
        }
    }

    private Order calculateTotalCostWithDiscounts(HashMap<Integer, Integer> purchases) throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Integer i : purchases.keySet()) {
            if(products.get(i)!=null){
                orderItems.add(OrderItem.Builder);
            }else{
                throw new Exception("Product not found");
            }

        }
    }

    private static void applyWholesaleDiscounts(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Product product = products.get(item.getProductID());
            if (product.isWholesaleProduct() && item.getQuantity() >= 5) {
                item.applyDiscount(new BigDecimal(0.1)); // 10% wholesale discount
            }
        }
    }

    private static DiscountCard findDiscountById(String discountCardName) throws IOException {
        for (DiscountCard discountCard : ParseDiscountCardsCSV.getCard(DISCOUNT_CARDS_FILE)) {
            if(discountCard.getCardNumber().equals(discountCardName)){
                return discountCard;
            }
        }
        // Implement logic to find discount by ID
        // (You might need to load discount cards from a file or database)
        return null; // Replace this with actual implementation
    }

    private static void checkBalanceAndWriteOrder(Order order) {
        BigDecimal totalCost = order.getTotalCost();

        if (balanceDebitCard == null || balanceDebitCard.compareTo(totalCost) < 0) {
            System.err.println("Error: Insufficient balance on debit card.");
            return;
        }

        try {
            OrderWriter.writeOrderToFile(RESULT_FILE, order, findDiscountById(discountCardId));
            System.out.println("Purchase successful. Total cost: " + totalCost);
        } catch (IOException e) {
            System.err.println("Error writing order to file: " + e.getMessage());
        }
    }
}
