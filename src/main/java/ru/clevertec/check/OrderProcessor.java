package ru.clevertec.check;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrderProcessor {
    private static double amount = 0;
    private static int discount = 0;

    public static double calc(HashMap<Integer, Integer> purchases, String discountCardNumber, List<Product> products) throws IOException {
        for (Integer i : purchases.keySet()) {
            double price = products.get(i).getPrice();
            int quantity = purchases.get(i);
            price *= quantity;
            amount += price;
        }
        if (discountCardNumber != null) {
            List<DiscountCard> discountCards = ParseDiscountCardsCSV.getCard(CheckRunner.DISCOUNT_CARDS_FILE);
            for (DiscountCard discountCard : discountCards) {
                if (discountCardNumber.equals(discountCard.getCardNumber())) {
                    discount = discountCard.getDiscount();
                }

            }
            amount *= 1 - ((double) discount / 100);
        }
        return amount;
    }


}
