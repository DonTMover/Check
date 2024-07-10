package ru.clevertec.check;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.exceptions.InternalServerErrorException;

public class SqlQueries {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static Connection connection;

    protected static Connection getConnection(String datasourceUrl, String datasourceUsername, String datasourcePassword) throws IOException {
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName(DB_DRIVER);
            Properties props = new Properties();
            props.setProperty("user", datasourceUsername);
            props.setProperty("password", datasourcePassword);
            connection = DriverManager.getConnection(datasourceUrl, props);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new BadRequestException(e.toString());
        }

    }

    protected static List<Product> getProducts(Connection connection) throws SQLException {
        if (connection == null) {
            throw new InternalServerErrorException("connection is null");
        }
        List<Product> products = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from product");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            products.add(new Product.Builder()
                    .setId(rs.getInt("id"))
                    .setName(rs.getString("description"))
                    .setPrice(rs.getDouble("price"))
                    .setDiscount(rs.getBoolean("wholesale_product"))
                    .setQuantityInStock(rs.getInt("quantity_in_stock"))
                    .build());
        }
        return products;
    }

    protected static List<DiscountCard> getDiscountCards(Connection connection) throws SQLException {
        if (connection == null) {
            throw new InternalServerErrorException("connection is null");
        }
        List<DiscountCard> discountCards = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from discount_card");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            discountCards.add(new DiscountCard.Builder()
                    .setDiscount(rs.getInt("amount"))
                    .setCardNumber(String.valueOf(rs.getInt("number")))
                    .setId(rs.getInt("id"))
                    .build());
        }
        return discountCards;
    }

    //Попытка добавить CRUD
    protected static boolean setNewProductsQuantityInStock(Connection connection, int quantityInStock, Product product) throws SQLException {
        if (connection == null) {
            throw new InternalServerErrorException("connection is null");
        }

        // Use PreparedStatement with placeholders for security and efficiency
        String sql = "UPDATE product SET quantity_in_stock = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            int productId = product.getId();
            ps.setInt(1, quantityInStock);  // Set new quantity
            ps.setInt(2, productId);        // Set product ID
            ps.addBatch(); // Add update statement to a batch for efficiency


            // Execute all updates in the batch
            ps.executeBatch();
            return true;
        }
    }

}
