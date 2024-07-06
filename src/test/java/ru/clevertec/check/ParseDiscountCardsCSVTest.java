package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParseDiscountCardsCSVTest {

    @Test
    void parseDiscountCardsCSV() throws IOException {
        List<DiscountCard> discountCards = ParseDiscountCardsCSV.parseDiscountCardsCSV(CheckRunner.DISCOUNT_CARDS_FILE);
        assertNotNull(discountCards);
    }
}