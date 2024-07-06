package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckRunnerTest {

    @Test
    void getBalanceDebitCard() {
        CheckRunner.setBalanceDebitCard(100);
        assertEquals(BigDecimal.valueOf(100.0), CheckRunner.getBalanceDebitCard());
    }

    @Test
    void getProducts() {
        List<Product> products = CheckRunner.getProducts();
        assertNull(products);
    }

    @Test
    void getDiscountCards() {
        List<DiscountCard> discountCards = CheckRunner.getDiscountCards();
        assertNull(discountCards);
    }

    @Test
    void getDiscountCardId() {
        assertNotNull(CheckRunner.getDiscountCardId());
    }

    @Test
    void setBalanceDebitCard() {
        CheckRunner.setBalanceDebitCard(100);
        assertEquals(BigDecimal.valueOf(100.0), CheckRunner.getBalanceDebitCard());
    }

    @Test
    void setDiscountCardId() {
        CheckRunner.setDiscountCardId("1111");
        assertEquals("1111", CheckRunner.getDiscountCardId());
    }

    @Test
    void setProducts() {
        List<Product> products = CheckRunner.getProducts();
        assertNull(products);
    }

    @Test
    void setDiscountCards() throws IOException {
        List<DiscountCard> discountCards = ParseDiscountCardsCSV.parseDiscountCardsCSV(CheckRunner.DISCOUNT_CARDS_FILE);
        CheckRunner.setDiscountCards(discountCards);
        assertNotNull(CheckRunner.getDiscountCardId());
    }
}