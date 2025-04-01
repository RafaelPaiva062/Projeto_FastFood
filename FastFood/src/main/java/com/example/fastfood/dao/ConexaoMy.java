package com.example.fastfood.dao;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
public class ConexaoMy {
    private static final String url ="jdbc:sqlite:myfastfood.db";
 public static Connection getConnection() throws SQLException {
     return DriverManager.getConnection(url);
 }
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conexao = conectar();
        if (conexao != null) {
            System.out.println("Conexão com SQLite bem-sucedida!");
        }
    }

}
