package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardTest {

    @Test
    void getId() {
        assertEquals(1, getDiscountCard().getId());
    }

    @Test
    void getCardNumber() {

        assertEquals("1111", getDiscountCard().getCardNumber());
    }

    @Test
    void getDiscount() {
        assertEquals(3, getDiscountCard().getDiscount());
    }

    @Test
    void findDiscountById() throws IOException {
        assertEquals(DiscountCard.findDiscountById("1111").getId(),getDiscountCard().getId());
    }

    private static DiscountCard getDiscountCard() {
        return new DiscountCard.Builder()
                .setDiscount(3)
                .setCardNumber("1111")
                .setId(1)
                .build();
    }
}