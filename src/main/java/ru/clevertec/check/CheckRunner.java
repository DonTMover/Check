package ru.clevertec.check;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.clevertec.check.OrderWriter.calculateTotalCostWithDiscounts;
import static ru.clevertec.check.OrderWriter.checkBalanceAndWriteOrder;


public class CheckRunner {
    protected static final String PRODUCTS_FILE = "./src/main/resources/products.csv";
    protected static final String DISCOUNT_CARDS_FILE = "./src/main/resources/discountCards.csv";
    protected static final String RESULT_FILE = "./result.csv";

    private static List<Product> products;
    private static Map<Integer, Integer> purchases;
    private static String discountCardId;
    private static BigDecimal balanceDebitCard;

    public static void main(String[] args) throws Exception {
        // Parse products CSV file
        products = ParseProductsCSV.parseProductsCSV(PRODUCTS_FILE);

        // Process command-line arguments
        parseArguments(args);

        // Check if purchase data is available
        if (purchases.isEmpty()) {
            System.err.println("Error: No purchase data found.");
            return;
        }
        for (Product product : products) {
            System.out.println(product.toString());
        }
        // Calculate total cost with discounts
        BigDecimal totalCostWithDiscounts = calculateTotalCostWithDiscounts(products, purchases,discountCardId);

        // Check balance and write order to file
        checkBalanceAndWriteOrder(totalCostWithDiscounts,DiscountCard.findDiscountById(String.valueOf(discountCardId)));
    }

    private static void parseArguments(String[] args) {
        purchases = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                discountCardId = arg.split("=")[1];
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

    public static BigDecimal getBalanceDebitCard() {
        return balanceDebitCard;
    }

    public static List<Product> getProducts() {
        return products;
    }
}
