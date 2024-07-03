package ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderWriter {
    private static final String CSV_FILE_NAME = "result.csv";
    private static final List<OrderItem> orderItems = new ArrayList<>();


    public static void checkBalanceAndWriteOrder(BigDecimal totalPrice, DiscountCard discountCard)
            throws IOException {
    // Calculate total discount (assuming logic for calculating discount is implemented elsewhere)
    double totalDiscount = new Order.Builder().addItems(orderItems).build().getTotalDiscount(); // Replace with your discount logic

    // Check balance and write order to file
    if (CheckRunner.getBalanceDebitCard().compareTo(totalPrice) < 0) {
        System.err.println("Error: Insufficient balance on debit card.");
        return;
    }

    try (FileWriter csvWriter = new FileWriter(CSV_FILE_NAME)) {
        // Write headers
        csvWriter.write("Date;Time\n");
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Use DD-MM format
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        csvWriter.write(date + ";" + time + "\n\n");  // Add an extra empty line for clarity

        // Write order details
        csvWriter.write("QTY; DESCRIPTION; PRICE; DISCOUNT; TOTAL\n");

        for (OrderItem orderItem : orderItems) {
            double discount = orderItem.getPrice() * calculateDiscount(orderItem,discountCard);
            double total = calculateTotalWithoutDiscount(orderItem,CheckRunner.getProducts());
            csvWriter.write(orderItem.getQuantity() + ";" + orderItem.getName() + ";"
            + total + "$;" + BigDecimal.valueOf(discount).setScale(2,RoundingMode.HALF_EVEN)
                    + "$;" + BigDecimal.valueOf(total*orderItem.getQuantity()).setScale(2,
                    RoundingMode.HALF_EVEN) + "$\n");
        }

        // Write discount card information
        csvWriter.write("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
        if (discountCard != null) {
            csvWriter.write(discountCard.getCardNumber() + ";" + discountCard.getDiscount() + "%\n");
        } else {
            csvWriter.write("None;0%\n");
        }

        // Write total price information
        csvWriter.write("\nTOTAL PRICE;" +  "TOTAL DISCOUNT;" +
                 "TOTAL WITH DISCOUNT" + "\n");
        csvWriter.write(totalPrice.setScale(2, RoundingMode.HALF_EVEN) + "$;" +
                (totalPrice.setScale(2, RoundingMode.HALF_EVEN).floatValue() -
                        BigDecimal.valueOf(totalDiscount).setScale(2, RoundingMode.HALF_EVEN).floatValue())
        + "$;" + (totalPrice.min(BigDecimal.valueOf(totalDiscount))).setScale(2, RoundingMode.HALF_EVEN) + "$");
    }
}



    private static double calculateTotalPrice(List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice() * product.getQuantityInStock();
        }
        return totalPrice;
    }


    private static double calculateTotalDiscount(List<Product> products, int discountPercentage) {
        return products.stream()
                .filter(Product::isWholesaleProduct) // Filter only discounted products
                .mapToDouble(product -> product.getPrice() * product.getQuantityInStock() * discountPercentage / 100)
                .sum();
    }

    private static double calculateTotalWithDiscount(double totalPrice, double totalDiscount) {
        return totalPrice - totalDiscount;
    }

    private static double applyWholesaleDiscount(Product product, int quantity, int discountCardPercentage) {
        if (product.isWholesaleProduct() && quantity >= 5) {
            // Apply 10% wholesale discount before regular discount
            return discountCardPercentage + 10;
        } else {
            return discountCardPercentage;
        }
    }

    private static double calculateTotalWithoutDiscount(OrderItem orderItem,List<Product> products){
        for (Product product : products) {

                if (product.getId()==orderItem.getProductID()){
                   return product.getPrice();

            }
        }
        return -1;
    }

    protected static BigDecimal calculateTotalCostWithDiscounts(List<Product> products,
                                                                Map<Integer, Integer> purchases,
                                                                String discountCardId) throws Exception {

        //List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : purchases.entrySet()) {
            Product product = products.get(entry.getKey());
            if (product == null) {
                throw new IllegalArgumentException("Product not found: ID " + entry.getKey()); // More specific error message
            }
            orderItems.add(new OrderItem.Builder()
                    .setQuantity(entry.getValue())  // Use entry.getValue() for quantity
                    .setPrice(product.getPrice())
                    .setName(product.getName())
                    .setProductID(entry.getKey())
                    .build());
        }

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getQuantity() > 5) {
                orderItem.applyDiscount(BigDecimal.valueOf(0.1));
            } else if (discountCardId != null) { // Use else if for exclusive discount application
                BigDecimal discount = BigDecimal.valueOf(DiscountCard.findDiscountById(discountCardId).getDiscount())
                        .divide(BigDecimal.valueOf(100));
                orderItem.applyDiscount(discount);
            }
        }

        Order order = new Order.Builder().addItems(orderItems).build();
        return order.getTotalPrice();
    }

    private static BigDecimal calculateTotalCostWithoutDiscounts(HashMap<Integer, Integer> purchases,
                                                                 List<Product> products) {
        //List<OrderItem> orderItems = new ArrayList<>();
        for (Integer i : purchases.keySet()) {
            if (products.get(i) != null) {
                orderItems.add(new OrderItem.Builder()
                        .setQuantity(purchases.get(i))
                        .setPrice(products.get(i).getPrice())
                        .setName(products.get(i).getName())
                        .setProductID(i)
                        .build());
            } else {
                throw new IllegalArgumentException("Product not found");
            }
        }
        Order order = new Order.Builder().addItems(orderItems).build();
        return order.getTotalPrice();
    }
    private static double calculateDiscount(OrderItem orderItem, DiscountCard discountCard) {
    if (orderItem.getQuantity() >= 5) {
        return orderItem.getPrice() * 0.1; // 10% discount for wholesale
    } else if (discountCard != null) {
        return orderItem.getPrice() * discountCard.getDiscount(); // Discount from card
    } else {
        return 0; // No discount
    }
}
    //TODO Написать такой-же вывод, но уже в консоль, так-же исправить баг из-за которого не выводятся продукты

//    private static void applyWholesaleDiscounts(Order order) {
//        for (OrderItem item : order.getOrderItems()) {
//            Product product = products.get(item.getProductID());
//            if (product.isWholesaleProduct() && item.getQuantity() >= 5) {
//                item.applyDiscount(new BigDecimal(0.1)); // 10% wholesale discount
//            }
//        }
//    }
}
