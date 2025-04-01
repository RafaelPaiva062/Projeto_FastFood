
package com.example.fastfood.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.fastfood.controllers.Pedidos;
import com.example.fastfood.controllers.Produto;
import com.example.fastfood.controllers.Lanche;
import com.example.fastfood.controllers.Bebida;
import com.example.fastfood.entites.Server;

public class Cordapios implements Server {

    @Override
    public void inserir(Produto produto) {
        String promocao = produto.getPromocao(); // Assuming Produto class has getPromocao method
        if (produto instanceof Lanche) {
            String sql = "INSERT INTO cardapio (nome, preco, tipo, vegano, promocao) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = ConexaoMy.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, produto.getNome_produto());
                stmt.setDouble(2, produto.getPreco_produto());
                stmt.setString(3, "Lanche");
                stmt.setBoolean(4, ((Lanche) produto).isVegano());
                stmt.setString(5, promocao);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao adicionar produto: " + e.getMessage());
            }
        } else if (produto instanceof Bebida) {
            String sql = "INSERT INTO cardapio (nome, preco, tipo, alcoolico, promocao) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = ConexaoMy.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, produto.getNome_produto());
                stmt.setDouble(2, produto.getPreco_produto());
                stmt.setString(3, "Bebida");
                stmt.setBoolean(4, ((Bebida) produto).isAlcolico());
                stmt.setString(5, promocao);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao adicionar produto: " + e.getMessage());
            }
        }
    }

    @Override
    public Produto buscarId(int id) {
        String sql = "SELECT * FROM cardapio WHERE id = ?";
        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                String tipo = rs.getString("tipo");
                String promocao = rs.getString("promocao");

                if ("Lanche".equals(tipo)) {
                    boolean vegano = rs.getBoolean("vegano");
                    return new Lanche(id, nome, preco, vegano, promocao);
                } else if ("Bebida".equals(tipo)) {
                    boolean alcoolica = rs.getBoolean("alcoolica");
                    return new Bebida(id, nome, preco, alcoolica, promocao);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void atualizar(Produto produto) {
        String promocao = produto.getPromocao(); // Assuming Produto class has getPromocao method
        if (produto instanceof Lanche) {
            String sql = "UPDATE cardapio SET nome = ?, preco = ?, tipo = ?, vegano = ?, promocao = ? WHERE id = ?";
            try (Connection conn = ConexaoMy.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, produto.getNome_produto());
                stmt.setDouble(2, produto.getPreco_produto());
                stmt.setString(3, "Lanche");
                stmt.setBoolean(4, ((Lanche) produto).isVegano());
                stmt.setString(5, promocao);
                stmt.setInt(6, produto.getId_produto());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar produto: " + e.getMessage());
            }
        } else if (produto instanceof Bebida) {
            String sql = "UPDATE cardapio SET nome = ?, preco = ?, tipo = ?, alcoolico = ?, promocao = ? WHERE id = ?";
            try (Connection conn = ConexaoMy.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, produto.getNome_produto());
                stmt.setDouble(2, produto.getPreco_produto());
                stmt.setString(3, "Bebida");
                stmt.setBoolean(4, ((Bebida) produto).isAlcolico());
                stmt.setString(5, promocao);
                stmt.setInt(6, produto.getId_produto());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar produto: " + e.getMessage());
            }
        }
    }

    @Override
    public void remover(int idproduto) {
        String sql = "DELETE FROM cardapio WHERE id = ?";
        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idproduto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao Bando de Dados: " + e.getMessage());
        }
    }

    @Override
    public List<Produto> listarProdutosT(String tipo) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM cardapio WHERE tipo = ?";

        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                String promocao = rs.getString("promocao");

                if ("Lanche".equals(tipo)) {
                    boolean vegano = rs.getBoolean("vegano");
                    produtos.add(new Lanche(id, nome, preco, vegano, promocao));
                } else if ("Bebida".equals(tipo)) {
                    boolean alcoolica = rs.getBoolean("alcoolica");
                    produtos.add(new Bebida(id, nome, preco, alcoolica, promocao));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos por tipo: " + e.getMessage());
        }
        return produtos;
    }

    @Override
    public List<Produto> lisTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM cardapio";

        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                String tipo = rs.getString("tipo");
                String promocao = rs.getString("promocao");

                if ("Lanche".equals(tipo)) {
                    boolean vegano = rs.getBoolean("vegano");
                    produtos.add(new Lanche(id, nome, preco, vegano, promocao));
                } else if ("Bebida".equals(tipo)) {
                    boolean alcoolica = rs.getBoolean("alcoolica");
                    produtos.add(new Bebida(id, nome, preco, alcoolica, promocao));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos por tipo: " + e.getMessage());
        }
        return produtos;
    }

    public void inserirHistorico(Pedidos pedido, String formaPagamento) {
        String produtos = pedido.listarProdutos(); // Obtém a lista de produtos do pedido
        double total = pedido.calcularTotal(); // Calcula o total do pedido
        String sql = "INSERT INTO pedidos (produtos, total, forma_pagamento, nome_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produtos);
            pstmt.setDouble(2, total);
            pstmt.setString(3, formaPagamento);
            pstmt.setString(4, pedido.getNomeUsuario());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar pedido ao histórico: " + e.getMessage());
        }
    }

    public List<String> historicoPedidos() {
        List<String> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos ORDER BY data DESC";
        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String produtos = rs.getString("produtos");
                String formaPagamento = rs.getString("forma_pagamento");
                String data = rs.getString("data");
                String nome = rs.getString("nome_usuario");
                String pedidoFormatado = String.format("0-0-0-0-0-0-0-00-0-0-0-0-00-0-0-\n" +
                        "Produtos: "+produtos+"\n" + "\n Forma de Pagamento: "+formaPagamento+" |\n" +
                        "\n Data: "+formaPagamento+"|\n" + "\n Nome: "+nome+"\n" + "0-0-0-0-0-0-0-00-0-0-0-0-00-0-0-\n");
                pedidos.add(pedidoFormatado);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar Histórico de Pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    @Override
    public List<Produto> buscarNome(String predicado) {
        List<Produto> produtosEncontrados = new ArrayList<>();
        String sql = "SELECT * FROM cardapio WHERE nome LIKE ? OR tipo LIKE ?";

        try (Connection conn = ConexaoMy.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Usando '%' para permitir busca parcial
            String busca = "%" + predicado + "%";
            stmt.setString(1, busca);
            stmt.setString(2, busca);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                String tipo = rs.getString("tipo");
                String promocao = rs.getString("promocao");

                if ("Lanche".equals(tipo)) {
                    boolean vegano = rs.getBoolean("vegano");
                    produtosEncontrados.add(new Lanche(id, nome, preco, vegano, promocao));
                } else if ("Bebida".equals(tipo)) {
                    boolean alcoolica = rs.getBoolean("alcoolica");
                    produtosEncontrados.add(new Bebida(id, nome, preco, alcoolica, promocao));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
        }
        return produtosEncontrados;
    }

    @Override
    public List<String> pesquisarPedidos(String nomeUsuario, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pesquisarPedidos'");
    }
}
