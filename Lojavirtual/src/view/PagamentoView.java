package view;

import controller.CarrinhoController;
import controller.PecaRoupaController;
import controller.VendaController;
import dao.PecaRoupaDAO;
import model.ItemCarrinho;
import model.PecaRoupa;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PagamentoView extends JFrame {
    private CarrinhoController carrinhoController;
    private PecaRoupaController pecaController;
    private VitrineView vitrineView;
    private CarrinhoView carrinhoView;

    private JRadioButton rbCredito;
    private JRadioButton rbDebito;
    private JRadioButton rbPix;
    private ButtonGroup grupoFormaPagamento;

    private JButton btnConfirmar;
    private JButton btnCancelar;

    public PagamentoView(PecaRoupaController pecaController, VitrineView vitrineView, CarrinhoView carrinhoView) {
        this.pecaController = pecaController;
        this.vitrineView = vitrineView;
        this.carrinhoView = carrinhoView;
        this.carrinhoController = CarrinhoController.getInstance();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Finalizar Compra");
        setSize(600, 700);
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

        JPanel painelResumo = new JPanel();
        painelResumo.setLayout(new BoxLayout(painelResumo, BoxLayout.Y_AXIS));
        painelResumo.setBackground(new Color(240, 248, 255));
        painelResumo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(102, 126, 234), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        painelResumo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblResumoTitulo = new JLabel("Resumo do Pedido");
        lblResumoTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblResumoTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelResumo.add(lblResumoTitulo);

        painelResumo.add(Box.createRigidArea(new Dimension(0, 10)));

        List<ItemCarrinho> itens = carrinhoController.getItens();

        JScrollPane scrollItens = new JScrollPane();
        scrollItens.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollItens.setBorder(null);
        scrollItens.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JPanel painelItens = new JPanel();
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        painelItens.setBackground(new Color(240, 248, 255));

        for (ItemCarrinho item : itens) {
            JLabel lblItem = new JLabel(String.format("%dx %s - R$ %.2f",
                    item.getQuantidade(),
                    item.getPeca().getNome(),
                    item.getSubtotal()));
            lblItem.setFont(new Font("Arial", Font.PLAIN, 13));
            lblItem.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelItens.add(lblItem);
            painelItens.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        scrollItens.setViewportView(painelItens);
        painelResumo.add(scrollItens);

        painelResumo.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblTotalItens = new JLabel(String.format("Total de Itens: %d", carrinhoController.getQuantidadeTotal()));
        lblTotalItens.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalItens.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelResumo.add(lblTotalItens);

        painelResumo.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblValorTotal = new JLabel(String.format("Valor Total: R$ %.2f", carrinhoController.getValorTotal()));
        lblValorTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblValorTotal.setForeground(new Color(76, 175, 80));
        lblValorTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelResumo.add(lblValorTotal);

        painelCentral.add(painelResumo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblFormaPagamento = new JLabel("Escolha a Forma de Pagamento:");
        lblFormaPagamento.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormaPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCentral.add(lblFormaPagamento);

        painelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

        grupoFormaPagamento = new ButtonGroup();

        rbCredito = criarOpcaoPagamento("Cartão de Crédito", "Parcelamento em até 3x sem juros");
        grupoFormaPagamento.add(rbCredito);
        painelCentral.add(rbCredito);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        rbDebito = criarOpcaoPagamento("Cartão de Débito", "À vista com desconto de 5%");
        grupoFormaPagamento.add(rbDebito);
        painelCentral.add(rbDebito);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        rbPix = criarOpcaoPagamento("PIX", "À vista com desconto de 10%");
        grupoFormaPagamento.add(rbPix);
        painelCentral.add(rbPix);

        rbPix.setSelected(true);

        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

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
        btnConfirmar.addActionListener(e -> finalizarCompra() );
        btnCancelar.addActionListener(e -> cancelar());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                cancelar();
            }
        });
    }

    /*private void confirmarCompra() { //verificar continuidade de uso apos atualizacao com banco
        String formaPagamento = "";
        BigDecimal valorTotal = carrinhoController.getValorTotal();
        double valorFinal = valorTotal.doubleValue();

        if (rbCredito.isSelected()) {
            formaPagamento = "Cartão de Crédito";
        } else if (rbDebito.isSelected()) {
            formaPagamento = "Cartão de Débito";
            valorFinal = valorFinal * 0.95;
        } else if (rbPix.isSelected()) {
            formaPagamento = "PIX";
            valorFinal = valorFinal * 0.90;
        }

        List<ItemCarrinho> itens = carrinhoController.getItens();
        for (ItemCarrinho item : itens) {
            pecaController.removerPeca(item.getPeca().getId());
        }

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Compra Confirmada com Sucesso!\n\n");
        mensagem.append("Itens Comprados:\n");

        for (ItemCarrinho item : itens) {
            mensagem.append(String.format("- %dx %s\n", item.getQuantidade(), item.getPeca().getNome()));
        }

        for (ItemCarrinho item : carrinhoController.getItens()) {
            PecaRoupa peca = item.getPeca();

            int novoEstoque = peca.getEstoque() - item.getQuantidade();
            peca.setEstoque(novoEstoque);

            new PecaRoupaDAO().atualizarEstoque(peca.getId(), novoEstoque);
        }


        mensagem.append(String.format("\nTotal de Itens: %d\n", carrinhoController.getQuantidadeTotal()));
        mensagem.append(String.format("Forma de Pagamento: %s\n", formaPagamento));
        mensagem.append(String.format("Valor Final: R$ %.2f\n\n", valorFinal));
        mensagem.append("Obrigado pela preferência!");

        JOptionPane.showMessageDialog(this,
                mensagem.toString(),
                "Compra Realizada",
                JOptionPane.INFORMATION_MESSAGE);

        carrinhoController.limparCarrinho();

        voltarVitrine();
    } // << verificar continuidade de uso /* */

    private void finalizarCompra() {
        BigDecimal total = carrinhoController.getValorTotal();
        List<ItemCarrinho> itens = carrinhoController.getItens();

        VendaController vendaController = new VendaController();

        boolean sucesso = vendaController.finalizarVenda(itens, total);

        if (sucesso) {
            carrinhoController.limparCarrinho();   // 4️⃣ limpar carrinho

            JOptionPane.showMessageDialog(this,
                    "Compra realizada com sucesso!\nObrigado pela preferência!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            //implementar voltar tela incial
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erro ao finalizar compra. Tente novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cancelar() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja cancelar a compra?",
                "Cancelar Compra",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            voltarCarrinho();
        }
    }

    private void voltarVitrine() {
        vitrineView.atualizarVitrine();
        vitrineView.setVisible(true);
        carrinhoView.dispose();
        this.dispose();
    }

    private void voltarCarrinho() {
        carrinhoView.setVisible(true);
        this.dispose();
    }
}