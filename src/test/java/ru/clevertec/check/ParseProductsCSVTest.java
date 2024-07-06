package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParseProductsCSVTest {

    @Test
    void parseProductsCSV() throws IOException {
        List<Product> products = ParseProductsCSV.parseProductsCSV(CheckRunner.PRODUCTS_FILE);
        assertNotNull(products);
    }
}