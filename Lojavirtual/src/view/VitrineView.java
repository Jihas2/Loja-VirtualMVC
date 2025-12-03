package view;

import controller.CarrinhoController;
import controller.PecaRoupaController;
import model.PecaRoupa;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

public class VitrineView extends JFrame {
    private PecaRoupaController controller;
    private CarrinhoController carrinhoController;
    private JPanel painelPecas;
    private JButton btnVoltar;
    private JButton btnAtualizar;
    private JButton btnCarrinho;
    private JLabel lblCarrinhoInfo;
    private Usuario usuarioLogado;

    public VitrineView(PecaRoupaController controller, Usuario usuario) {
        this.controller = controller;
        this.usuarioLogado = usuario;
        this.carrinhoController = CarrinhoController.getInstance();
        inicializarComponentes();
        carregarPecas();
    }

    private void inicializarComponentes() {
        setTitle("Roupas a venda - Exibição ao Público");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(new Color(30, 30, 33));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Produtos em Catálogo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(190, 0, 0));
        painelSuperior.add(lblTitulo, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(new Color(30, 30, 33));

        lblCarrinhoInfo = new JLabel("Carrinho: 0 itens");
        lblCarrinhoInfo.setFont(new Font("Arial", Font.BOLD, 13));
        lblCarrinhoInfo.setForeground(Color.WHITE);
        lblCarrinhoInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        btnCarrinho = new JButton("Ver Carrinho");
        btnCarrinho.setPreferredSize(new Dimension(140, 35));
        btnCarrinho.setBackground(new Color(255, 193, 7));
        btnCarrinho.setForeground(Color.BLACK);
        btnCarrinho.setFont(new Font("Arial", Font.BOLD, 12));
        btnCarrinho.setFocusPainted(false);
        btnCarrinho.setBorderPainted(false);
        btnCarrinho.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setPreferredSize(new Dimension(120, 35));
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setForeground(new Color(30, 30, 33));
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(120, 35));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(30, 30, 33));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(lblCarrinhoInfo);
        painelBotoes.add(btnCarrinho);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);
        painelSuperior.add(painelBotoes, BorderLayout.EAST);

        add(painelSuperior, BorderLayout.NORTH);

        painelPecas = new JPanel();
        painelPecas.setLayout(new GridLayout(0, 3, 20, 20));
        painelPecas.setBackground(new Color(245, 245, 250));
        painelPecas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(painelPecas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        configurarEventos();
    }

    private void configurarEventos() {
        btnVoltar.addActionListener(e -> {
            new TelaInicialView(usuarioLogado).setVisible(true);
            this.dispose();
        });
        btnAtualizar.addActionListener(e -> {
            carregarPecas();
            JOptionPane.showMessageDialog(this, "Vitrine atualizada!", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        btnCarrinho.addActionListener(e -> abrirCarrinho());
    }

    private void carregarPecas() {
        painelPecas.removeAll();
        List<PecaRoupa> pecas = controller.listarPecas();

        if (pecas.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhuma peça cadastrada ainda", SwingConstants.CENTER);
            lblVazio.setFont(new Font("Arial", Font.BOLD, 18));
            lblVazio.setForeground(Color.GRAY);
            painelPecas.add(lblVazio);
        } else {
            for (PecaRoupa peca : pecas) {
                painelPecas.add(criarCardPeca(peca));
            }
        }

        atualizarInfoCarrinho();
        painelPecas.revalidate();
        painelPecas.repaint();
    }

    public void atualizarVitrine() {
        carregarPecas();
    }

    private void atualizarInfoCarrinho() {
        int quantidade = carrinhoController.getQuantidadeTotal();
        lblCarrinhoInfo.setText(String.format("Carrinho: %d %s",
                quantidade,
                quantidade == 1 ? "item" : "itens"));
    }

    private JPanel criarCardPeca(PecaRoupa peca) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(350, 500));

        JPanel painelImagem = new JPanel();
        painelImagem.setLayout(new BorderLayout());
        painelImagem.setPreferredSize(new Dimension(320, 250));
        painelImagem.setBackground(new Color(240, 240, 240));
        painelImagem.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        JLabel lblImagem = new JLabel();
        lblImagem.setHorizontalAlignment(SwingConstants.CENTER);

        if (peca.getCaminhoImagem() != null && !peca.getCaminhoImagem().isEmpty()) {
            try {
                File imgFile = new File(peca.getCaminhoImagem());
                if (imgFile.exists()) {
                    BufferedImage img = ImageIO.read(imgFile);
                    Image scaledImg = img.getScaledInstance(300, 240, Image.SCALE_SMOOTH);
                    lblImagem.setIcon(new ImageIcon(scaledImg));
                } else {
                    lblImagem.setText("Imagem não encontrada");
                    lblImagem.setFont(new Font("Arial", Font.PLAIN, 14));
                    lblImagem.setForeground(Color.GRAY);
                }
            } catch (Exception e) {
                lblImagem.setText("Erro ao carregar imagem");
                lblImagem.setFont(new Font("Arial", Font.PLAIN, 14));
                lblImagem.setForeground(Color.GRAY);
            }
        } else {
            lblImagem.setText("Sem imagem");
            lblImagem.setFont(new Font("Arial", Font.PLAIN, 14));
            lblImagem.setForeground(Color.GRAY);
        }

        painelImagem.add(lblImagem, BorderLayout.CENTER);
        card.add(painelImagem, BorderLayout.NORTH);

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBackground(Color.WHITE);
        painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel lblNome = new JLabel(peca.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 18));
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInfo.add(lblNome);

        painelInfo.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblDetalhes = new JLabel(peca.getTipo() + " - Tamanho: " + peca.getTamanho());
        lblDetalhes.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDetalhes.setForeground(new Color(100, 100, 100));
        lblDetalhes.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInfo.add(lblDetalhes);

        if (peca.getCor() != null && !peca.getCor().isEmpty()) {
            painelInfo.add(Box.createRigidArea(new Dimension(0, 3)));
            JLabel lblCor = new JLabel("Cor: " + peca.getCor());
            lblCor.setFont(new Font("Arial", Font.PLAIN, 12));
            lblCor.setForeground(new Color(120, 120, 120));
            lblCor.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelInfo.add(lblCor);
        }

        painelInfo.add(Box.createRigidArea(new Dimension(0, 8)));

        if (peca.getDescricao() != null && !peca.getDescricao().isEmpty()) {
            JTextArea txtDescricao = new JTextArea(peca.getDescricao());
            txtDescricao.setFont(new Font("Arial", Font.PLAIN, 11));
            txtDescricao.setForeground(new Color(80, 80, 80));
            txtDescricao.setLineWrap(true);
            txtDescricao.setWrapStyleWord(true);
            txtDescricao.setEditable(false);
            txtDescricao.setOpaque(false);
            txtDescricao.setRows(2);
            txtDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelInfo.add(txtDescricao);
            painelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JLabel lblEstoque = new JLabel("Estoque: " + peca.getEstoque());
        lblEstoque.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstoque.setForeground(new Color(80, 80, 80));
        lblEstoque.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInfo.add(lblEstoque);

        painelInfo.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel painelPreco = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelPreco.setBackground(Color.WHITE);
        painelPreco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPreco = new JLabel(String.format("R$ %.2f", peca.getPreco()));
        lblPreco.setFont(new Font("Arial", Font.BOLD, 24));
        lblPreco.setForeground(new Color(76, 175, 80));
        painelPreco.add(lblPreco);

        painelInfo.add(painelPreco);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnAdicionarCarrinho = new JButton("ADICIONAR AO CARRINHO");
        btnAdicionarCarrinho.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAdicionarCarrinho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnAdicionarCarrinho.setPreferredSize(new Dimension(300, 40));
        btnAdicionarCarrinho.setBackground(new Color(76, 175, 80));
        btnAdicionarCarrinho.setForeground(Color.WHITE);
        btnAdicionarCarrinho.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdicionarCarrinho.setFocusPainted(false);
        btnAdicionarCarrinho.setBorderPainted(false);
        btnAdicionarCarrinho.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (peca.getEstoque() <= 0) {
            btnAdicionarCarrinho.setEnabled(false);
            btnAdicionarCarrinho.setText("ESGOTADO");
            btnAdicionarCarrinho.setBackground(new Color(150, 150, 150));
        }

        btnAdicionarCarrinho.addActionListener(e -> adicionarAoCarrinho(peca));

        btnAdicionarCarrinho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btnAdicionarCarrinho.isEnabled()) {
                    btnAdicionarCarrinho.setBackground(new Color(67, 160, 71));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnAdicionarCarrinho.isEnabled()) {
                    btnAdicionarCarrinho.setBackground(new Color(76, 175, 80));
                }
            }
        });

        painelInfo.add(btnAdicionarCarrinho);

        card.add(painelInfo, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 30, 33), 2),
                        BorderFactory.createEmptyBorder(14, 14, 14, 14)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });

        return card;
    }

    private void adicionarAoCarrinho(PecaRoupa peca) {
        int quantidadeAtual = carrinhoController.getQuantidadeProduto(peca.getId());

        if (quantidadeAtual >= peca.getEstoque()) {
            JOptionPane.showMessageDialog(this,
                    "Você já adicionou o máximo disponível no estoque!",
                    "Estoque insuficiente",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (peca.getEstoque() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Estoque esgotado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        carrinhoController.adicionarPeca(peca);
        atualizarInfoCarrinho();
        JOptionPane.showMessageDialog(this,
                peca.getNome() + " adicionado ao carrinho!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirCarrinho() {
        if (carrinhoController.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seu carrinho está vazio!",
                    "Carrinho Vazio",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        CarrinhoView carrinhoView = new CarrinhoView(controller, this);
        carrinhoView.setVisible(true);
        this.setVisible(false);
    }

    private void voltarParaLista() {
        ListaView listaView = new ListaView(controller, usuarioLogado);
        listaView.setVisible(true);
        this.dispose();
    }
}
