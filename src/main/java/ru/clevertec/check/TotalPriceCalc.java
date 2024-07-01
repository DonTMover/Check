package ru.clevertec.check;

import java.util.HashMap;
import java.util.List;

public class TotalPriceCalc {
    private static double amount = 0;

    public static double calc(HashMap<Integer, Integer> purchases, boolean isHaveDebitCard, List<Product> products) {
        for (Integer i : purchases.keySet()) {
            double price = products.get(i).getPrice();
            int quantity = purchases.get(i);
            price *= quantity;
            amount += price;
        }
        if (isHaveDebitCard) {

        }
        return 0.0;
    }
}
