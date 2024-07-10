package ru.clevertec.check;

import ru.clevertec.check.exceptions.BadRequestException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.clevertec.check.OrderWriter.calculateTotalCostWithDiscounts;
import static ru.clevertec.check.OrderWriter.checkBalanceAndWriteOrder;


public class CheckRunner {
    protected static String RESULT_FILE;
    protected static String URL;
    protected static String USERNAME;
    protected static String PASSWORD;

    private static List<Product> products;
    private static List<DiscountCard> discountCards;
    private static Map<Integer, Integer> purchases;
    private static String discountCardId;
    private static BigDecimal balanceDebitCard;

    public static void main(String[] args) throws Exception {


        // Process command-line arguments
        parseArguments(args);

        CheckFilePaths.checkFiles(RESULT_FILE, USERNAME, PASSWORD, URL);

        Connection connection = SqlQueries.getConnection(URL, USERNAME, PASSWORD);
        products = SqlQueries.getProducts(connection);
        discountCards = SqlQueries.getDiscountCards(connection);
        connection.close();

        // Check if purchase data is available
        if (purchases.isEmpty()) {
            System.err.println("Error: No purchase data found.");
            return;
        }
        // Calculate total cost with discounts
        BigDecimal totalCostWithDiscounts = calculateTotalCostWithDiscounts(products, purchases, discountCardId);

        // Check balance and write order to file
        checkBalanceAndWriteOrder(totalCostWithDiscounts, DiscountCard.findDiscountById(String.valueOf(discountCardId)));

        for (OrderItem orderItem : OrderWriter.getOrderItems()) {
            int id = orderItem.getProductID();
            Product productt = null;
            for (Product product : products) {
                if(orderItem.getProductID() == product.getId()){
                    productt = product;
                }
            }
            SqlQueries.setNewProductsQuantityInStock(connection,orderItem.getQuantity(),productt);
        }


    }

    private static void parseArguments(String[] args) throws IOException {
        purchases = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                discountCardId = arg.split("=")[1];
            } else if (arg.startsWith("balanceDebitCard=")) {
                if (arg != null) {
                    balanceDebitCard = new BigDecimal(arg.split("=")[1]);
                } else {
                    throw new BadRequestException("Balance Debit Card Not Found");
                }
            } else if (arg.startsWith("saveToFile=")) {
                RESULT_FILE = arg.split("=")[1];
            } else if (arg.startsWith("datasource.url=")) {
                URL = arg.split("=")[1];
            } else if (arg.startsWith("datasource.username=")) {
                USERNAME = arg.split("=")[1];
            } else if (arg.startsWith("datasource.password=")) {
                PASSWORD = arg.split("=")[1];
            } else {
                try {
                    String[] parts = arg.split("-");
                    int productId = Integer.parseInt(parts[0]);
                    int quantity = Integer.parseInt(parts[1]);
                    purchases.put(productId, quantity);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Invalid Product ID");
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

    public static List<DiscountCard> getDiscountCards() {
        return discountCards;
    }

    public static String getDiscountCardId() {
        return discountCardId;
    }

    public static void setBalanceDebitCard(double balanceDebitCard1) {
        balanceDebitCard = BigDecimal.valueOf(balanceDebitCard1);
    }

    public static void setDiscountCardId(String DiscountCardId) {
        discountCardId = DiscountCardId;
    }

    public static void setProducts(List<Product> products) {
        CheckRunner.products = products;
    }

    public static void setDiscountCards(List<DiscountCard> discountCards) {
        CheckRunner.discountCards = discountCards;
    }

    protected static String getURL() {
        return URL;
    }

    protected static String getUsername() {
        return USERNAME;
    }

    protected static String getPassword() {
        return PASSWORD;
    }

    protected static void setURL(String URL) {
        CheckRunner.URL = URL;
    }

    protected static void setUsername(String username) {
        CheckRunner.USERNAME = username;
    }

    protected static void setPassword(String password) {
        CheckRunner.PASSWORD = password;
    }
}
