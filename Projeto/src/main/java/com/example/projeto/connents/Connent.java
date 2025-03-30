package com.example.projeto.connents;

import java.sql.*;

public class Connent {
    public static void main(String[] args) {
        // Initialize the connection, statement, and result set
        try {
            // Create the connection
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/fastfoods", "root", "Prof@dm1n"
            );

            // Create the statement
            Statement statement = connection.createStatement();

            // Execute a query (change the table name to 'produto' or any valid table name)
            ResultSet resultSet = statement.executeQuery("SELECT * FROM produto");

            // Iterate through the result set
            while (resultSet.next()) {
                System.out.println(resultSet.getString("nome")); // Example column name
                System.out.println(resultSet.getFloat("preco")); // Example column name
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
