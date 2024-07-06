package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private static String name = "Milk";

    @Test
    void getId() throws IOException {
        assertEquals(1,getProduct().getId());
    }

    @Test
    void getName() throws IOException {
        assertEquals(name,getProduct().getName());
    }

    @Test
    void getPrice() throws IOException {
        assertEquals(1.07,getProduct().getPrice(),0.3);
    }

    @Test
    void isWholesaleProduct() throws IOException {
        assertEquals(true,getProduct().isWholesaleProduct());
    }

    @Test
    void getPriceWithDiscount() throws IOException {
        assertEquals(1.07,getProduct().getPriceWithDiscount(),0.6);
    }

    @Test
    void getQuantityInStock() throws IOException {
        assertEquals(10,getProduct().getQuantityInStock());
    }

    @Test
    void setQuantityInStock() throws IOException {
        Product product = getProduct();
        product.setQuantityInStock(11);
        assertEquals(11,product.getQuantityInStock());
    }

    @Test
    void getProductByID() throws IOException {
        assertEquals(getProduct(),Product.getProductByID(1));
    }
    private static Product getProduct() throws IOException {
        CheckRunner.setProducts(ParseProductsCSV.parseProductsCSV(CheckRunner.PRODUCTS_FILE));
        return new Product.Builder()
                .setDiscount(true)
                .setId(1)
                .setName(name)
                .setQuantityInStock(10)
                .setPrice(1.07)
                .build();
    }
}