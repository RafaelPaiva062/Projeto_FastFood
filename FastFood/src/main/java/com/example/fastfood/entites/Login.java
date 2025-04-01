package com.example.fastfood.entites;

import com.example.fastfood.FastFood;

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private static JTextField userField = null;
    private final JPasswordField passField;

    public Login() {
        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(45, 45, 45));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setForeground(Color.WHITE);
        userField = new JTextField(15);
        userField.setBackground(new Color(60, 60, 60));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);

        JLabel passLabel = new JLabel("Senha:");
        passLabel.setForeground(Color.WHITE);
        passField = new JPasswordField(15);
        passField.setBackground(new Color(60, 60, 60));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Registrar");
        estilizarBotao(loginButton);
        estilizarBotao(registerButton);

        formPanel.add(userLabel, gbc);
        gbc.gridy++;
        formPanel.add(userField, gbc);
        gbc.gridy++;
        formPanel.add(passLabel, gbc);
        gbc.gridy++;
        formPanel.add(passField, gbc);
        gbc.gridy++;
        formPanel.add(loginButton, gbc);
        gbc.gridy++;
        formPanel.add(registerButton, gbc);

        panel.add(formPanel);
        add(panel);

        loginButton.addActionListener(_ -> autenticarUsuario());
        registerButton.addActionListener(_ -> {
            // Trocar para o código que inicializa a tela de registro
            JOptionPane.showMessageDialog(this, "Funcionalidade de registro ainda não implementada.");
        });
    }

    private void autenticarUsuario() {
        String usuario = userField.getText();
        String senha = new String(passField.getPassword());

        if (validarLogin(usuario, senha)) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            SwingUtilities.invokeLater(() -> new FastFood(usuario));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarLogin(String usuario, String senha) {
        String url = "jdbc:sqlite:myfastfood.db";
        String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getNomeUsuario() {
        return userField.getText();
    }

    private static void estilizarBotao(JButton botao) {
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }
}