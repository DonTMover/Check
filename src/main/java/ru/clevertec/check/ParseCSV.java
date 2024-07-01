package ru.clevertec.check;

import java.io.*;
import java.util.*;

public class ParseCSV {
    private static Product parseProductLine(String line) {
  // Split the line by comma
  String[] tokens = line.split(",");

  // Validate data length (should match number of product fields)
  if (tokens.length != 6) {
    System.err.println("Invalid line format: " + line);
    return null;
  }

  // Parse values
  try {
    int id = Integer.parseInt(tokens[0]);
    String name = tokens[1];
    double price = Double.parseDouble(tokens[2]);
    int quantityInStock = Integer.parseInt(tokens[3]);  // Assuming quantity is also included
    boolean discount = Boolean.parseBoolean(tokens[4]);

    // Create Product using builder (assuming quantityInStock and wholesaleProduct are not used)
    return new Product.Builder(id)
        .name(name)
        .price(price)
        .discount(discount)
        .build();
  } catch (NumberFormatException e) {
    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
    return null;
  }
}

   public static List<Product> parseProductsCSV(String filePath) throws IOException {
  List<Product> products = new ArrayList<>();
  BufferedReader reader = new BufferedReader(new FileReader(filePath));
  String line;
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
