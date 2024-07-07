package ru.clevertec.check;

import java.io.IOException;
import java.util.ArrayList;

public class DiscountCard {
    private static ArrayList<DiscountCard> discountCards = new ArrayList<>();
    private int id;
    private String cardNumber;
    private int discount;

    public DiscountCard(int id, String cardNumber, int discount) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getDiscount() {
        return discount;
    }

    public static class Builder {
        private int id;
        private String cardNumber;
        private int discount;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setCardNumber(String cardNumber) {
            if (cardNumber!=null) {
                this.cardNumber = cardNumber;
            }else{
                this.cardNumber = null;
                this.discount = 0;
                this.id = -1;
            }
            return this;
        }

        public Builder setDiscount(int discount) {
            this.discount = discount;
            return this;
        }

        public DiscountCard build() {
            DiscountCard discountCard = new DiscountCard(id, cardNumber, discount);
            discountCards.add(discountCard);
            return discountCard;
        }


    }

    protected static DiscountCard findDiscountById(String discountCardName) throws IOException {
        for (DiscountCard discountCard : ParseDiscountCardsCSV.parseDiscountCardsCSV(CheckRunner.DISCOUNT_CARDS_FILE)) {
            if (discountCard.getCardNumber().equals(discountCardName)) {
                return discountCard;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", discount=" + discount +
                '}';
    }
}
