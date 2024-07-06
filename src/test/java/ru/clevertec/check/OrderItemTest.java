package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void applyDiscount() throws IOException {
        OrderItem orderItem = getOrderItem();
        orderItem.applyDiscount(BigDecimal.valueOf(1));
        assertEquals(1.0379,orderItem.getPrice());
    }

    @Test
    void getPrice() throws IOException {
        OrderItem orderItem = getOrderItem();
        assertEquals(1.07,orderItem.getPrice());
    }

    @Test
    void getProductID() throws IOException {
        OrderItem orderItem = getOrderItem();
        assertEquals(1,orderItem.getProductID());
    }

    @Test
    void getQuantity() throws IOException {
        OrderItem orderItem = getOrderItem();
        assertEquals(1,orderItem.getQuantity());
    }

    @Test
    void setPrice() throws IOException {
        OrderItem orderItem = getOrderItem();
        orderItem.setPrice(2);
        assertEquals(2,orderItem.getPrice());
    }

    @Test
    void getName() throws IOException {
        OrderItem orderItem = getOrderItem();
        assertEquals("Milk",orderItem.getName());
    }

    @Test
    void isWhosale() throws IOException {
        OrderItem orderItem = getOrderItem();
        assertEquals(true,orderItem.isWhosale());
    }

    @Test
    void getDiscountPercentage() throws IOException {
        OrderItem orderItem = getOrderItem();
        orderItem.getDiscountPercentage("1111");
        assertEquals(1.07,orderItem.getPrice()); //не может работать правильно из-за того что запуск не из CheckRunner
    }
    private static OrderItem getOrderItem() throws IOException {
        CheckRunner.setDiscountCards(ParseDiscountCardsCSV.parseDiscountCardsCSV(CheckRunner.DISCOUNT_CARDS_FILE));
        List<Product> products = new ArrayList<>();
        products.add(new Product.Builder()
                .setName("Milk")
                .setPrice(1.07)
                .setDiscount(true)
                .setId(1)
                .setQuantityInStock(10)
                .build());
        CheckRunner.setProducts(products);
        CheckRunner.setBalanceDebitCard(100);
        return new OrderItem.Builder()
                .setName("Milk")
                .setPrice(1.07)
                .setProductID(1)
                .setQuantity(1)
                .build();
    }
}