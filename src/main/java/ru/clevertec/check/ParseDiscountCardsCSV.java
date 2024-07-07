package ru.clevertec.check;

import ru.clevertec.check.exceptions.InternalServerErrorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseDiscountCardsCSV {
    private static DiscountCard parseProductLine(String line) {
        // Split the line by comma
        String[] tokens = line.split(",");
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
            throw new InternalServerErrorException("Error parsing product line " + line + ": " + e.getMessage());
        }
    }

    public static List<DiscountCard> parseDiscountCardsCSV(String filePath) throws IOException {
        List<DiscountCard> discountCards = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Skip the first line (header)
        reader.readLine();

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
