package ru.clevertec.check;

import ru.clevertec.check.exceptions.InternalServerErrorException;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ParseProductsCSV {
    private static Product parseProductLine(String line) {
        // Split the line by comma
        String[] tokens = line.split(",");

        // Validate data length (should match number of product fields)
//        if (tokens.length != 6) {
//            System.err.println("Invalid line format: " + line);
//            return null;
//        }

        try {
            boolean discount = false;
            int id = Integer.parseInt(tokens[0]);
            String name = tokens[1];
            BigDecimal price = new BigDecimal(tokens[2]);  // Use BigDecimal for price
            int quantityInStock = Integer.parseInt(tokens[3]);
            if(tokens[4].equals("+")){
                discount = true;
            }

            // Create Product using builder
            return new Product.Builder()
                    .setId(id)
                    .setQuantityInStock(quantityInStock)
                    .setName(name)
                    .setPrice(Double.parseDouble(String.valueOf(price)))
                    .setDiscount(discount)
                    .build();
        } catch (NumberFormatException e) {
            throw new InternalServerErrorException("Error parsing line: " + line + " - " + e.getMessage());
        }
    }


    public static List<Product> parseProductsCSV(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Skip the first line (header)
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            // Process each line
            Product product = parseProductLine(line);
            if (product != null) {
                products.add(product);
            }
        }
        reader.close();
        return products;
    }


}
