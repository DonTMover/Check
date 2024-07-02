package ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseDiscountCardsCSV {
    private static DiscountCard parseProductLine(String line) {
        // Split the line by comma
        String[] tokens = line.split(",");

        // Validate data length (should match number of product fields)
        if (tokens.length != 4) {
            System.err.println("Invalid line format: " + line);
            return null;
        }

        // Parse values
        try {
            int id = Integer.parseInt(tokens[0]);
            String cardNumber = tokens[1];
            int discount = Integer.parseInt(tokens[2]);

            // Create DiscountCard using builder

            return new DiscountCard.Builder()
                    .setDiscount(discount)
                    .setCardNumber(cardNumber)
                    .setId(id)
                    .build();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    public static List<DiscountCard> getCard(String filePath) throws IOException {
        List<DiscountCard> discountCards = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            // Process each line
            DiscountCard discountCard = parseProductLine(line);
            if (discountCard != null) {
                discountCards.add(discountCard);
            }
        }
        reader.close();
        return discountCards;

    }
}
