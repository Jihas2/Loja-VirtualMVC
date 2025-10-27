package view;

import controller.PecaRoupaController;
import model.PecaRoupa;
import javax.swing.*;
import java.awt.*;

public class PagamentoView extends JFrame {
    private PecaRoupa peca;
    private VitrineView vitrineView;
    private PecaRoupaController controller;

    private JRadioButton rbCredito;
    private JRadioButton rbDebito;
    private JRadioButton rbPix;
    private ButtonGroup grupoFormaPagamento;

    private JButton btnConfirmar;
    private JButton btnCancelar;

    public PagamentoView(PecaRoupa peca, VitrineView vitrineView, PecaRoupaController controller) {
        this.peca = peca;
        this.vitrineView = vitrineView;
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Finalizar Compra");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(15, 15));
        painelPrincipal.setBackground(new Color(245, 245, 250));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Finalizar Compra", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(102, 126, 234));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(Color.WHITE);
        painelCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel painelProduto = new JPanel();
        painelProduto.setLayout(new BoxLayout(painelProduto, BoxLayout.Y_AXIS));
        painelProduto.setBackground(new Color(240, 248, 255));
        painelProduto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(102, 126, 234), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        painelProduto.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblProdutoTitulo = new JLabel("Resumo do Pedido");
        lblProdutoTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblProdutoTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelProduto.add(lblProdutoTitulo);

        painelProduto.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblNomeProduto = new JLabel("Produto: " + peca.getNome());
        lblNomeProduto.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNomeProduto.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelProduto.add(lblNomeProduto);

        painelProduto.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblTipoProduto = new JLabel("Tipo: " + peca.getTipo() + " | Tamanho: " + peca.getTamanho());
        lblTipoProduto.setFont(new Font("Arial", Font.PLAIN, 13));
        lblTipoProduto.setForeground(Color.GRAY);
        lblTipoProduto.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelProduto.add(lblTipoProduto);

        if (peca.getCor() != null && !peca.getCor().isEmpty()) {
            painelProduto.add(Box.createRigidArea(new Dimension(0, 5)));
            JLabel lblCorProduto = new JLabel("Cor: " + peca.getCor());
            lblCorProduto.setFont(new Font("Arial", Font.PLAIN, 13));
            lblCorProduto.setForeground(Color.GRAY);
            lblCorProduto.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelProduto.add(lblCorProduto);
        }

        painelProduto.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblPrecoProduto = new JLabel(String.format("Valor Total: R$ %.2f", peca.getPreco()));
        lblPrecoProduto.setFont(new Font("Arial", Font.BOLD, 20));
        lblPrecoProduto.setForeground(new Color(76, 175, 80));
        lblPrecoProduto.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelProduto.add(lblPrecoProduto);

        painelCentral.add(painelProduto);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));

        // Formas de pagamento
        JLabel lblFormaPagamento = new JLabel("Escolha a Forma de Pagamento:");
        lblFormaPagamento.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormaPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCentral.add(lblFormaPagamento);

        painelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

        grupoFormaPagamento = new ButtonGroup();

        // Crédito
        rbCredito = criarOpcaoPagamento("Cartão de Crédito", "Parcelamento em até 3x sem juros");
        grupoFormaPagamento.add(rbCredito);
        painelCentral.add(rbCredito);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        // Débito
        rbDebito = criarOpcaoPagamento("Cartão de Débito", "À vista com desconto de 5%");
        grupoFormaPagamento.add(rbDebito);
        painelCentral.add(rbDebito);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        // PIX
        rbPix = criarOpcaoPagamento("PIX", "À vista com desconto de 10%");
        grupoFormaPagamento.add(rbPix);
        painelCentral.add(rbPix);

        // Selecionar PIX por padrão
        rbPix.setSelected(true);

        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelBotoes.setBackground(new Color(245, 245, 250));

        btnConfirmar = new JButton("Confirmar Compra");
        btnConfirmar.setPreferredSize(new Dimension(180, 45));
        btnConfirmar.setBackground(new Color(76, 175, 80));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(140, 45));
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(btnConfirmar);
        painelBotoes.add(btnCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        configurarEventos();
    }

    private JRadioButton criarOpcaoPagamento(String titulo, String descricao) {
        JRadioButton rb = new JRadioButton();
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb.setBackground(Color.WHITE);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rb.setText("<html><div style='padding: 5px;'><b style='font-size: 15px;'>" + titulo +
                "</b><br><span style='font-size: 12px; color: gray;'>" + descricao +
                "</span></div></html>");
        rb.setFont(new Font("Arial", Font.PLAIN, 14));

        return rb;
    }

    private void configurarEventos() {
        btnConfirmar.addActionListener(e -> confirmarCompra());
        btnCancelar.addActionListener(e -> cancelar());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                cancelar();
            }
        });
    }

    private void confirmarCompra() {
        String formaPagamento = "";
        double valorFinal = peca.getPreco().doubleValue();

        if (rbCredito.isSelected()) {
            formaPagamento = "Cartão de Crédito";
        } else if (rbDebito.isSelected()) {
            formaPagamento = "Cartão de Débito";
            valorFinal = valorFinal * 0.95;
        } else if (rbPix.isSelected()) {
            formaPagamento = "PIX";
            valorFinal = valorFinal * 0.90;
        }

        controller.removerPeca(peca.getId());

        String mensagem = String.format(
                "Compra Confirmada com Sucesso!\n\n" +
                        "Produto: %s\n" +
                        "Forma de Pagamento: %s\n" +
                        "Valor Final: R$ %.2f\n\n" +
                        "Obrigado pela preferência!",
                peca.getNome(),
                formaPagamento,
                valorFinal
        );

        JOptionPane.showMessageDialog(this,
                mensagem,
                "Compra Realizada",
                JOptionPane.INFORMATION_MESSAGE);

        voltarVitrine();
    }

    private void cancelar() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja cancelar a compra?",
                "Cancelar Compra",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            voltarVitrine();
        }
    }

    private void voltarVitrine() {
        vitrineView.atualizarVitrine(); // Atualizar a vitrine
        vitrineView.setVisible(true);
        this.dispose();
    }
}