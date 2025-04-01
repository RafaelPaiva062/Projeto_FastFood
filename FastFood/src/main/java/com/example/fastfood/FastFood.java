package com.example.fastfood;

import com.example.fastfood.controllers.Bebida;
import com.example.fastfood.controllers.Lanche;
import com.example.fastfood.controllers.Pedidos;
import com.example.fastfood.controllers.Produto;
import com.example.fastfood.dao.Cordapios;
import com.example.fastfood.entites.Login;
import com.example.fastfood.entites.Server;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FastFood extends JFrame {
    private final JFrame frame;
    private final Server cardapios = new Cordapios();

    private Pedidos pedido = new Pedidos(Login.getNomeUsuario());

    public FastFood(String usuarioLogado) {
        frame = new JFrame("Fast Food");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.getContentPane().setBackground(new Color(45, 45, 45)); // Dark background
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(30, 30, 30));
        tabbedPane.setForeground(Color.white);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        JPanel gerenciamentoPanel = new JPanel(new GridBagLayout());
        gerenciamentoPanel.setBackground(new Color(60, 60, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton btnCadastrar = criarBotao("Cadastrar Produto", new Color(94, 255, 0));
        JButton btnRemover = criarBotao("Remover Produto", new Color(20, 220, 77));
        JButton btnAtualizar = criarBotao("Atualizar Produto", new Color(0, 255, 196));
        JButton btnHistorico = criarBotao("Histórico de Pedidos", new Color(255, 0, 68));
        JButton btnPesquisarPedidos = criarBotao("Pesquisar Pedidos", new Color(0, 178, 255));
        JButton btnListar = criarBotao("Ver Cardápio", new Color(0, 255, 4));
        JButton btnListarPedido = criarBotao("Ver Pedido", new Color(0, 255, 255));

        gbc.gridx = 0; gbc.gridy = 0;
        gerenciamentoPanel.add(btnCadastrar, gbc);
        gbc.gridy++;
        gerenciamentoPanel.add(btnRemover, gbc);
        gbc.gridy++;
        gerenciamentoPanel.add(btnAtualizar, gbc);
        gbc.gridy++;
        gerenciamentoPanel.add(btnHistorico, gbc);
        gbc.gridy++;
        gerenciamentoPanel.add(btnPesquisarPedidos, gbc);
        gbc.gridy++;
        gerenciamentoPanel.add(btnListar, gbc);
        gbc.gridy++;
        gerenciamentoPanel.add(btnListarPedido, gbc);

        tabbedPane.addTab("Gerenciamento", gerenciamentoPanel);

        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setBackground(new Color(18, 15, 15)); // White background
        JLabel userLabel = new JLabel("Usuário Logado: " + usuarioLogado);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setForeground(new Color(255, 255, 255));
        gbc.gridy = 0;
        userPanel.add(userLabel, gbc);
        tabbedPane.addTab("Usuário", userPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        btnHistorico.addActionListener(_ -> mostrarHistoricoPedidos());
        btnCadastrar.addActionListener(_ -> abrirCadastro());
        btnRemover.addActionListener(_ -> removerProduto());
        btnAtualizar.addActionListener(_ -> atualizarProduto());
        btnListar.addActionListener(_ -> listarProdutos());
        btnListarPedido.addActionListener(_ -> mostrarPedido());
        btnPesquisarPedidos.addActionListener(_ -> pesquisarPedidos());

        frame.setVisible(true);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return botao;
    }

    private void pesquisarPedidos() {
        JFrame pesquisaFrame = new JFrame("Pesquisar Pedidos");
        pesquisaFrame.setSize(400, 200);
        pesquisaFrame.setLayout(new GridLayout(3, 2));

        JTextField nomeUsuarioField = new JTextField();
        JTextField dataField = new JTextField();
        JButton btnPesquisar = criarBotao("Pesquisar", new Color(0, 178, 255));

        btnPesquisar.addActionListener(_ -> {
            String nomeUsuario = nomeUsuarioField.getText().trim();
            String data = dataField.getText().trim();

            if (nomeUsuario.isEmpty() && data.isEmpty()) {
                JOptionPane.showMessageDialog(pesquisaFrame, "Por favor, insira pelo menos um critério de pesquisa.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> resultados = cardapios.pesquisarPedidos(nomeUsuario, data);
            if (!resultados.isEmpty()) {
                StringBuilder resultado = new StringBuilder("Pedidos encontrados:\n");
                for (String pedido : resultados) {
                    resultado.append(pedido).append("\n");
                }
                JOptionPane.showMessageDialog(pesquisaFrame, resultado.toString());
            } else {
                JOptionPane.showMessageDialog(pesquisaFrame, "Nenhum pedido encontrado com os critérios fornecidos.");
            }
        });

        pesquisaFrame.add(new JLabel("Nome do Usuário:"));
        pesquisaFrame.add(nomeUsuarioField);
        pesquisaFrame.add(new JLabel("Data (YYYY-MM-DD):"));
        pesquisaFrame.add(dataField);
        pesquisaFrame.add(btnPesquisar);

        pesquisaFrame.setVisible(true);
    }

    //JFrame para Cadastro de Produtos + Tratamento de Exceções
    private void abrirCadastro() {
        JFrame cadastroFrame = new JFrame("Cadastrar Produto");
        cadastroFrame.setSize(300, 250);
        cadastroFrame.setLayout(new GridLayout(6, 2));

        JTextField nomeField = new JTextField();
        JTextField precoField = new JTextField();
        JTextField promocaoField = new JTextField(); // New field for promotion
        String[] tipos = {"Lanche", "Bebida"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);
        JCheckBox extraCheck = new JCheckBox("Vegano / Alcoólico");
        JButton btnSalvar = criarBotao("Salvar", new Color(34, 139, 34)); // Verde

        btnSalvar.addActionListener(_ -> {
            String nome = nomeField.getText().trim();
            String precoStr = precoField.getText().trim();
            String promocao = promocaoField.getText().trim(); // Get promotion text

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(cadastroFrame, "O nome do produto não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(precoStr);
                if (preco < 0) {
                    JOptionPane.showMessageDialog(cadastroFrame, "O preço não pode ser negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(cadastroFrame, "Preço inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipo = (String) tipoBox.getSelectedItem();

            if ("Lanche".equals(tipo)) {
                cardapios.inserir(new Lanche(0, nome, preco, extraCheck.isSelected(), promocao));
            } else {
                cardapios.inserir(new Bebida(0, nome, preco, extraCheck.isSelected(), promocao));
            }
            cadastroFrame.dispose();
        });

        cadastroFrame.add(new JLabel("Nome:"));
        cadastroFrame.add(nomeField);
        cadastroFrame.add(new JLabel("Preço:"));
        cadastroFrame.add(precoField);
        cadastroFrame.add(new JLabel("Promoção:")); // Label for promotion
        cadastroFrame.add(promocaoField); // Add promotion field
        cadastroFrame.add(new JLabel("Tipo:"));
        cadastroFrame.add(tipoBox);
        cadastroFrame.add(extraCheck);
        cadastroFrame.add(btnSalvar);

        cadastroFrame.setVisible(true);
    }

    //JFrame para saída do Cardápio
    private void listarProdutos() {
        JFrame cardapioFrame = new JFrame("Cardápio");
        cardapioFrame.setSize(400, 300);
        cardapioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cardapioFrame.setLayout(new BorderLayout()); // Usando BorderLayout

        // Cria um JTabbedPane para as abas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Cria o painel para Lanches
        JPanel lanchesPanel = new JPanel();
        lanchesPanel.setLayout(new BoxLayout(lanchesPanel, BoxLayout.Y_AXIS)); // Layout vertical

        // Obtém a lista de lanches e adiciona ao painel
        List<Produto> lanches = cardapios.lisTodos();
        for (Produto produto : lanches) {
            if (produto instanceof Lanche) {
                lanchesPanel.add(new JLabel(produto.toString())); // Adiciona cada lanche como um JLabel
            }
        }

        // Cria o painel para Bebidas
        JPanel bebidasPanel = new JPanel();
        bebidasPanel.setLayout(new BoxLayout(bebidasPanel, BoxLayout.Y_AXIS)); // Layout vertical

        for (Produto produto : lanches) {
            if (produto instanceof Bebida) {
                bebidasPanel.add(new JLabel(produto.toString())); // Adiciona cada bebida como um JLabel
            }
        }

        // Adiciona os painéis ao JTabbedPane
        tabbedPane.addTab("Lanches", lanchesPanel);
        tabbedPane.addTab("Bebidas", bebidasPanel);

        // Adiciona o JTabbedPane ao JFrame
        cardapioFrame.add(tabbedPane, BorderLayout.CENTER); // Adiciona o JTabbedPane na área central

        // Cria o botão "Adicionar ao Pedido"
        JButton btnAdicionarPedido = criarBotao("Adicionar ao Pedido", new Color(34, 139, 34)); // Verde
        btnAdicionarPedido.addActionListener(_ -> selecionarProdutoParaPedido());

        // Adiciona o botão na parte inferior do JFrame
        cardapioFrame.add(btnAdicionarPedido, BorderLayout.SOUTH); // Adiciona o botão na área sul

        // Torna o JFrame visível
        cardapioFrame.setVisible(true);
    }

    private void selecionarProdutoParaPedido() {
        String[] tipos = {"Lanche", "Bebida"};
        String tipoSelecionado = (String) JOptionPane.showInputDialog(frame,
                "Selecione o tipo de produto:",
                "Listar por Tipo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]);

        if (tipoSelecionado == null) {
            // O usuário cancelou a seleção de tipo.
            JOptionPane.showMessageDialog(frame, "Operação cancelada.");
            return;
        }

        // Obtém os produtos de acordo com o tipo selecionado
        List<Produto> produtos = cardapios.listarProdutosT(tipoSelecionado);

        if (produtos.isEmpty()) {
            // Se não houver produtos disponíveis, exiba uma mensagem ao usuário
            JOptionPane.showMessageDialog(frame, "Não há produtos disponíveis para o tipo selecionado.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cria um array de opções para o JOptionPane
        String[] opcoes = produtos.stream().map(Produto::toString).toArray(String[]::new);

        // Exibe o JOptionPane para seleção do produto
        String produtoSelecionado = (String) JOptionPane.showInputDialog(frame,
                "Selecione um produto:",
                "Adicionar ao Pedido",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (produtoSelecionado != null) {
            // Adiciona o produto selecionado ao pedido
            for (Produto p : produtos) {
                if (produtoSelecionado.contains(p.getNome_produto())) {
                    pedido.adicionarProduto(p);
                    JOptionPane.showMessageDialog(frame, "Produto adicionado ao pedido!");
                    break;
                }
            }
        } else {
            // O usuário cancelou a seleção do produto
            JOptionPane.showMessageDialog(frame, "Operação cancelada.");
        }
    }

    private void mostrarPedido() {
        JFrame pedidoFrame = new JFrame("Detalhes do Pedido");
        pedidoFrame.setSize(400, 400);
        pedidoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pedidoFrame.setLayout(new BorderLayout()); // Usando BorderLayout

        // Painel para os produtos no pedido
        JPanel produtosPanel = new JPanel();
        produtosPanel.setLayout(new BoxLayout(produtosPanel, BoxLayout.Y_AXIS));

        // Adiciona os produtos ao painel
        if (pedido.getProdutos().isEmpty()) {
            produtosPanel.add(new JLabel("Nenhum produto no pedido."));
        } else {
            for (Produto produto : pedido.getProdutos()) {
                produtosPanel.add(new JLabel(produto.toString()));
            }
        }

        // Exibindo o total do pedido
        JLabel totalLabel = new JLabel("Total: R$" + pedido.calcularTotal());
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        produtosPanel.add(totalLabel);

        // Painel com botões de ação
        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new FlowLayout());

        // Botão para finalizar o pedido
        JButton btnFinalizarPedido = criarBotao("Finalizar Pedido", new Color(34, 139, 34)); // Verde
        btnFinalizarPedido.addActionListener(_ -> finalizarPedido(pedidoFrame));

        // Botão para remover produtos
        JButton btnRemoverProduto = criarBotao("Remover Produto", Color.RED); // Vermelho
        btnRemoverProduto.addActionListener(_ -> removerProduto());

        botoesPanel.add(btnFinalizarPedido);
        botoesPanel.add(btnRemoverProduto);

        // Adiciona os componentes ao Frame
        pedidoFrame.add(new JScrollPane(produtosPanel), BorderLayout.CENTER); // Produtos com barra de rolagem
        pedidoFrame.add(botoesPanel, BorderLayout.SOUTH); // Botões embaixo

        // Torna a janela visível
        pedidoFrame.setVisible(true);
    }
    //Função para atualizar Produtos através de ID
    private void atualizarProduto() {
        String idStr = JOptionPane.showInputDialog(frame, "Digite o ID do produto a atualizar:");
        if (idStr == null || idStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "ID não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "ID inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Produto produto = cardapios.buscarId(id);
        if (produto != null) {
            String novoNome = JOptionPane.showInputDialog(frame, "Novo nome:", produto.getNome_produto());
            if (novoNome == null || novoNome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "O nome não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String novoPrecoStr = JOptionPane.showInputDialog(frame, "Novo preço:", produto.getPreco_produto());
            if (novoPrecoStr == null || novoPrecoStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "O preço não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double novoPreco;
            try {
                novoPreco = Double.parseDouble(novoPrecoStr);
                if (novoPreco < 0) {
                    JOptionPane.showMessageDialog(frame, "O preço não pode ser negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Preço inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String novaPromocao = JOptionPane.showInputDialog(frame, "Nova promoção:", produto.getPromocao());
            if (novaPromocao == null) {
                novaPromocao = ""; // Default to empty if canceled
            }

            if (produto instanceof Lanche) {
                boolean isVegano = JOptionPane.showConfirmDialog(frame, "É vegano?", "Atualizar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                cardapios.atualizar(new Lanche(id, novoNome, novoPreco, isVegano, novaPromocao));
            } else if (produto instanceof Bebida) {
                boolean isAlcoolica = JOptionPane.showConfirmDialog(frame, "É alcoólico?", "Atualizar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                cardapios.atualizar(new Bebida(id, novoNome, novoPreco, isAlcoolica, novaPromocao));
            }
            JOptionPane.showMessageDialog(frame, "Produto atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Produto não encontrado.");
        }
    }

    private void removerProduto() {
        String idStr = JOptionPane.showInputDialog(frame, "Digite o ID do produto a remover:");

        // Verifica se o input não é nulo e contém apenas dígitos
        if (idStr != null && idStr.matches("\\d+")) {
            int id = Integer.parseInt(idStr);
            Produto produto = cardapios.buscarId(id);
            if (produto != null) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover este produto?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cardapios.remover(id);
                    JOptionPane.showMessageDialog(frame, "Produto removido com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Produto não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Entrada inválida! Por favor, digite um número inteiro válido.");
        }
    }

    private void finalizarPedido(JFrame pedidoFrame) {
        if (pedido.getProdutos().isEmpty()) {
            JOptionPane.showMessageDialog(pedidoFrame, "Não há produtos no pedido.");
            return;
        }

        String[] opcoesPagamento = {"Débito", "Crédito", "Pix"};
        String pagamentoSelecionado = (String) JOptionPane.showInputDialog(pedidoFrame, "Selecione a forma de pagamento:", "Finalizar Pedido", JOptionPane.QUESTION_MESSAGE, null, opcoesPagamento, opcoesPagamento[0]);

        if (pagamentoSelecionado != null) {
            JOptionPane.showMessageDialog(pedidoFrame, "Pedido finalizado com sucesso!\nTotal: R$" + pedido.calcularTotal() + "\nForma de pagamento: " + pagamentoSelecionado);

            cardapios.inserirHistorico(pedido, pagamentoSelecionado);

            pedido = new Pedidos(Login.getNomeUsuario());
            pedidoFrame.dispose();
        }
    }

    private void mostrarHistoricoPedidos() {
        JFrame historicoFrame = new JFrame("Histórico de Pedidos");
        historicoFrame.setSize(400, 300);
        historicoFrame.setLayout(new BorderLayout());
        historicoFrame.getContentPane().setBackground(new Color(50, 50, 50)); // Dark background

        JTextArea textoHistorico = new JTextArea();
        textoHistorico.setEditable(false);
        textoHistorico.setFont(new Font("Arial", Font.PLAIN, 14)); // Slightly larger font
        textoHistorico.setForeground(Color.WHITE); // White text color
        textoHistorico.setBackground(new Color(30, 30, 30)); // Darker text area background
        textoHistorico.setLineWrap(true);
        textoHistorico.setWrapStyleWord(true);
        textoHistorico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        try {
            // Load order history from the database
            List<String> historico = cardapios.historicoPedidos();
            if (historico == null || historico.isEmpty()) {
                textoHistorico.setText("Nenhum histórico de pedidos encontrado.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (String pedido : historico) {
                    sb.append(pedido).append("\n");
                }
                textoHistorico.setText(sb.toString());
            }
        } catch (Exception e) {
            textoHistorico.setText("Erro ao carregar histórico de pedidos: " + e.getMessage());
        }

        // Add a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(textoHistorico);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Border for the scroll pane
        historicoFrame.add(scrollPane, BorderLayout.CENTER);

        // Button to close the window
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(70, 70, 70)); // Button background
        btnFechar.setForeground(Color.WHITE); // Button text color
        btnFechar.setFocusPainted(false);
        btnFechar.addActionListener(_ -> historicoFrame.dispose());
        historicoFrame.add(btnFechar, BorderLayout.SOUTH);

        historicoFrame.setVisible(true);
    }

    public static void main(String[] ignoredArgs) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}