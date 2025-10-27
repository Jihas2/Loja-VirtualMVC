package view;

import controller.PecaRoupaController;
import model.PecaRoupa;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

public class VitrineView extends JFrame {
    private PecaRoupaController controller;
    private JPanel painelPecas;
    private JButton btnVoltar;
    private JButton btnAtualizar;

    public VitrineView(PecaRoupaController controller) {
        this.controller = controller;
        inicializarComponentes();
        carregarPecas();
    }

    private void inicializarComponentes() {
        setTitle("Vitrine de Peças - Exibição ao Público");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(new Color(102, 126, 234));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("VITRINE DE PEÇAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        painelSuperior.add(lblTitulo, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(new Color(102, 126, 234));

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setPreferredSize(new Dimension(120, 35));
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setForeground(new Color(102, 126, 234));
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(120, 35));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(102, 126, 234));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        btnVoltar.addActionListener(e -> voltarParaLista());
        btnAtualizar.addActionListener(e -> {
            carregarPecas();
            JOptionPane.showMessageDialog(this, "Vitrine atualizada!", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
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

        painelPecas.revalidate();
        painelPecas.repaint();
    }

    public void atualizarVitrine() {
        carregarPecas();
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
            String icone = obterIconePorTipo(peca.getTipo());
            lblImagem.setText("<html><div style='text-align: center; font-size: 48px;'>" + icone +
                    "<br><span style='font-size: 14px; color: gray;'>Sem imagem</span></div></html>");
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

        JLabel lblDetalhes = new JLabel(peca.getTipo() + " • Tamanho: " + peca.getTamanho());
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

        JPanel painelPreco = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelPreco.setBackground(Color.WHITE);
        painelPreco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPreco = new JLabel(String.format("R$ %.2f", peca.getPreco()));
        lblPreco.setFont(new Font("Arial", Font.BOLD, 24));
        lblPreco.setForeground(new Color(76, 175, 80));
        painelPreco.add(lblPreco);

        painelInfo.add(painelPreco);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnComprar = new JButton("COMPRAR AGORA");
        btnComprar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnComprar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnComprar.setPreferredSize(new Dimension(300, 40));
        btnComprar.setBackground(new Color(76, 175, 80));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFont(new Font("Arial", Font.BOLD, 14));
        btnComprar.setFocusPainted(false);
        btnComprar.setBorderPainted(false);
        btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnComprar.addActionListener(e -> abrirPagamento(peca));

        // Efeito hover no botão
        btnComprar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(new Color(67, 160, 71));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnComprar.setBackground(new Color(76, 175, 80));
            }
        });

        painelInfo.add(btnComprar);

        card.add(painelInfo, BorderLayout.CENTER);

        // Efeito hover no card
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(102, 126, 234), 2),
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

    private void abrirPagamento(PecaRoupa peca) {
        PagamentoView pagamentoView = new PagamentoView(peca, this, controller);
        pagamentoView.setVisible(true);
        this.setVisible(false);
    }

    private String obterIconePorTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "camiseta":
            case "camisa":
            case "blusa":
                return "";
            case "calça":
            case "shorts":
                return "";
            case "vestido":
                return "";
            case "saia":
                return "";
            case "jaqueta":
            case "casaco":
                return "";
            default:
                return "";
        }
    }

    private void voltarParaLista() {
        ListaView listaView = new ListaView(controller);
        listaView.setVisible(true);
        this.dispose();
    }
}