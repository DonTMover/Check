package ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderWriter {
    private static final String CSV_FILE_NAME = "result.csv";

    private static void printTheReceipt() {

    }

    public static void writeOrderToFile(HashMap<Integer, Integer> purchases, List<Product> products, List<DiscountCard> discountCards, String discountCard) throws IOException {
        int discountCardPercentage = 0;
        for (DiscountCard card : discountCards) {
            if (card.getCardNumber().equals(discountCard)) {
                discountCardPercentage = card.getDiscount();
            } else if (discountCard != null) {
                discountCardPercentage = 2;
            }
        }
        //Дата и ее формат
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        double totalPrice = 0;
        double totalDiscount = 0;
        for (Map.Entry<Integer, Integer> entry : purchases.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            // Find the corresponding product
            Product product = products.stream()
                    .filter(p -> p.getId() == productId)
                    .findFirst()
                    .orElse(null); // Handle missing product

            if (product != null) {
                double price = product.getPrice();
                double discount = applyWholesaleDiscount(product, quantity, discountCardPercentage);
                double total = price * quantity;
                totalPrice += total;
                totalDiscount += product.isWholesaleProduct() ? total * discount / 100 : 0;
            }
        }


        try (FileWriter csvWriter = new FileWriter(CSV_FILE_NAME)) {
            // Write headers
            csvWriter.write("Date;Time\n");
            csvWriter.write(date + ";" + time + "\n");

            // Write product data headers
            csvWriter.write("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

            // Write product data
            for (Product product : products) {
                double discount = product.isWholesaleProduct() ? product.getPrice() * discountCardPercentage / 100 : 0;
                double total = product.getPrice() * product.getQuantityInStock();
                csvWriter.write(product.getQuantityInStock() + ";" + product.getName() + ";" +
                        product.getPrice() + "; " + discount + "; " + total + "\n");
            }

            // Write discount card information
            csvWriter.write("DISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            csvWriter.write(discountCard + ";" + discountCardPercentage + "%\n");

            // Write total values
            csvWriter.write("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
            csvWriter.write(calculateTotalPrice(products) + "; " + calculateTotalDiscount(products, discountCardPercentage) + "; " + calculateTotalWithDiscount((calculateTotalPrice(products)),calculateTotalDiscount(products,discountCardPercentage)) + "\n");
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

}
