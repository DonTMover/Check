package ru.clevertec.check;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CheckRunner {
    public static final String PRODUCTS_FILE = "./src/main/resources/products.csv";
    public static final String DISCOUNT_CARDS_FILE = "./src/main/resources/discountCards.csv";


    private static Integer discountCard;
    private static Integer balanceDebitCard;
    private static HashMap<Integer,Integer> purchases;
    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            if(arg.startsWith("discountCard=")){
                String[] str = arg.split("=");
                discountCard = Integer.parseInt(str[1]);
            }
            if(arg.startsWith("balanceDebitCard=")){
                String[] str = arg.split("=");
                balanceDebitCard = Integer.parseInt(str[1]);
            }
            else {
                String[] str = arg.split("-");
                purchases.put(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
            }
            List<Product> products = ParseProductsCSV.parseProductsCSV(PRODUCTS_FILE);

        }
    }
}
