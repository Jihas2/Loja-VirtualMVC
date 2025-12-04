package view;

import controller.CarrinhoController;
import controller.PecaRoupaController;
import model.ItemCarrinho;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class CarrinhoView extends JFrame {
    private PecaRoupaController controller;
    private CarrinhoController carrinhoController;
    private VitrineView vitrineView;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JLabel lblTotal;
    private JButton btnFinalizarCompra;
    private JButton btnVoltar;
    private JButton btnLimpar;

    public CarrinhoView(PecaRoupaController controller, VitrineView vitrineView) {
        this.controller = controller;
        this.carrinhoController = CarrinhoController.getInstance();
        this.vitrineView = vitrineView;
        inicializarComponentes();
        carregarItens();
    }

    private void inicializarComponentes() {
        setTitle("Carrinho de Compras");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        JLabel lblTitulo = new JLabel("Carrinho de Compras", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(102, 126, 234));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"Produto", "Tipo", "Tamanho", "Preço Unit.", "Qtd", "Estoque", "Subtotal", "Ações"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 7; // Qtd e Ações são editáveis
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(45);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(102, 126, 234));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));

        // Configurar editor e renderizador para a coluna de Quantidade
        tabela.getColumn("Qtd").setCellRenderer(new QuantidadeRenderer());
        tabela.getColumn("Qtd").setCellEditor(new QuantidadeEditor());

        tabela.getColumn("Ações").setCellRenderer(new ButtonRenderer());
        tabela.getColumn("Ações").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));
        painelInferior.setBackground(new Color(245, 245, 250));

        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotal.setBackground(Color.WHITE);
        painelTotal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        lblTotal = new JLabel("Total: R$ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 22));
        lblTotal.setForeground(new Color(76, 175, 80));
        painelTotal.add(lblTotal);

        painelInferior.add(painelTotal, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(new Color(245, 245, 250));

        btnVoltar = new JButton("Continuar Comprando");
        btnVoltar.setPreferredSize(new Dimension(180, 45));
        btnVoltar.setBackground(new Color(102, 126, 234));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLimpar = new JButton("Limpar Carrinho");
        btnLimpar.setPreferredSize(new Dimension(150, 45));
        btnLimpar.setBackground(new Color(244, 67, 54));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 13));
        btnLimpar.setFocusPainted(false);
        btnLimpar.setBorderPainted(false);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnFinalizarCompra = new JButton("Finalizar Compra");
        btnFinalizarCompra.setPreferredSize(new Dimension(180, 45));
        btnFinalizarCompra.setBackground(new Color(76, 175, 80));
        btnFinalizarCompra.setForeground(Color.WHITE);
        btnFinalizarCompra.setFont(new Font("Arial", Font.BOLD, 13));
        btnFinalizarCompra.setFocusPainted(false);
        btnFinalizarCompra.setBorderPainted(false);
        btnFinalizarCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFinalizarCompra);

        painelInferior.add(painelBotoes, BorderLayout.CENTER);

        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

        add(painelPrincipal);

        configurarEventos();
    }

    private void configurarEventos() {
        btnVoltar.addActionListener(e -> voltarParaVitrine());
        btnLimpar.addActionListener(e -> limparCarrinho());
        btnFinalizarCompra.addActionListener(e -> finalizarCompra());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                voltarParaVitrine();
            }
        });
    }

    private void carregarItens() {
        modeloTabela.setRowCount(0);
        List<ItemCarrinho> itens = carrinhoController.getItens();

        for (ItemCarrinho item : itens) {
            Object[] linha = {
                    item.getPeca().getNome(),
                    item.getPeca().getTipo(),
                    item.getPeca().getTamanho(),
                    String.format("R$ %.2f", item.getPeca().getPreco()),
                    item.getQuantidade(),
                    item.getPeca().getEstoque() + " un.",
                    String.format("R$ %.2f", item.getSubtotal()),
                    "Ações"
            };
            modeloTabela.addRow(linha);
        }

        atualizarTotal();

        // Força a atualização visual da tabela
        tabela.repaint();
        tabela.revalidate();
    }

    private void atualizarTotal() {
        lblTotal.setText(String.format("Total: R$ %.2f", carrinhoController.getValorTotal()));
    }

    private void limparCarrinho() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente limpar o carrinho?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            carrinhoController.limparCarrinho();
            carregarItens();
            JOptionPane.showMessageDialog(this,
                    "Carrinho limpo com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validarEstoque() {
        List<ItemCarrinho> itens = carrinhoController.getItens();
        StringBuilder itensExcedentes = new StringBuilder();
        boolean estoqueValido = true;

        for (ItemCarrinho item : itens) {
            if (item.getQuantidade() > item.getPeca().getEstoque()) {
                estoqueValido = false;
                itensExcedentes.append("• ").append(item.getPeca().getNome())
                        .append(": Quantidade no carrinho (").append(item.getQuantidade())
                        .append(") excede o estoque (").append(item.getPeca().getEstoque())
                        .append(")\n");
            }
        }

        if (!estoqueValido) {
            JOptionPane.showMessageDialog(this,
                    "Não é possível finalizar a compra!\n\n" +
                            "Os seguintes itens excedem o estoque disponível:\n\n" +
                            itensExcedentes.toString() +
                            "\nPor favor, ajuste as quantidades antes de finalizar.",
                    "Estoque Insuficiente",
                    JOptionPane.ERROR_MESSAGE);
        }

        return estoqueValido;
    }

    private void finalizarCompra() {
        if (carrinhoController.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seu carrinho está vazio!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar estoque antes de prosseguir
        if (!validarEstoque()) {
            return;
        }

        PagamentoView pagamentoView = new PagamentoView(controller, vitrineView, this);
        pagamentoView.setVisible(true);
        this.setVisible(false);
    }

    private void voltarParaVitrine() {
        vitrineView.atualizarVitrine();
        vitrineView.setVisible(true);
        this.dispose();
    }

    private void removerItem(int row) {
        ItemCarrinho item = carrinhoController.getItens().get(row);
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja remover " + item.getPeca().getNome() + " do carrinho?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            carrinhoController.removerPeca(item.getPeca().getId());
            carregarItens();
        }
    }

    private void incrementarItem(int row) {
        ItemCarrinho item = carrinhoController.getItens().get(row);
        int estoque = item.getPeca().getEstoque();

        // Não permite incrementar se já atingiu o estoque
        if (item.getQuantidade() >= estoque) {
            JOptionPane.showMessageDialog(this,
                    "Quantidade máxima atingida!\nEstoque disponível: " + estoque + " unidades",
                    "Estoque Insuficiente",
                    JOptionPane.WARNING_MESSAGE);
            return; // Não faz nada, apenas retorna
        }

        // Só incrementa se ainda tiver estoque disponível
        carrinhoController.incrementarQuantidade(item.getPeca().getId());
        carregarItens();
    }

    private void decrementarItem(int row) {
        ItemCarrinho item = carrinhoController.getItens().get(row);
        if (item.getQuantidade() > 1) {
            carrinhoController.decrementarQuantidade(item.getPeca().getId());
            carregarItens();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Quantidade mínima é 1. Use o botão remover para excluir o item.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void atualizarQuantidade(int row, int novaQuantidade) {
        ItemCarrinho item = carrinhoController.getItens().get(row);
        int estoque = item.getPeca().getEstoque();

        // Não permite quantidade maior que o estoque
        if (novaQuantidade > estoque) {
            // Ajusta a quantidade
            item.setQuantidade(estoque);

            // Mostra a mensagem
            JOptionPane.showMessageDialog(this,
                    "Quantidade solicitada excede o estoque disponível!\n" +
                            "Estoque disponível: " + estoque + " unidades\n" +
                            "Você tentou adicionar: " + novaQuantidade + " unidades\n\n" +
                            "A quantidade foi ajustada para o máximo disponível: " + estoque,
                    "Estoque Insuficiente",
                    JOptionPane.ERROR_MESSAGE);

            // Recarrega a tabela para mostrar o novo valor
            carregarItens();
            return;
        }

        // Validação quantidade mínima é 1
        if (novaQuantidade < 1) {
            // Primeiro ajusta
            item.setQuantidade(1);

            JOptionPane.showMessageDialog(this,
                    "Quantidade mínima é 1. Use o botão remover para excluir o item.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);

            // FINALMENTE recarrega
            carregarItens();
            return;
        }

        // Se passou nas validações, atualiza a quantidade
        item.setQuantidade(novaQuantidade);
        carregarItens();
    }

    // Renderizador para a coluna de Quantidade com visual editável
    class QuantidadeRenderer extends JTextField implements TableCellRenderer {
        public QuantidadeRenderer() {
            setHorizontalAlignment(JTextField.CENTER);
            setFont(new Font("Arial", Font.BOLD, 14));
            setBackground(new Color(255, 255, 230)); // Amarelo claro
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(102, 126, 234), 2),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            setEditable(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? "" + value.toString() : "");

            if (isSelected) {
                setBackground(new Color(255, 255, 200));
            } else {
                setBackground(new Color(255, 255, 230));
            }

            return this;
        }
    }

    // Editor para a coluna de Quantidade
    class QuantidadeEditor extends DefaultCellEditor {
        private JTextField textField;
        private int currentRow;

        public QuantidadeEditor() {
            super(new JTextField());

            textField = (JTextField) getComponent();
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setFont(new Font("Arial", Font.BOLD, 14));
            textField.setBackground(new Color(255, 255, 200)); // Amarelo forte para edição
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(76, 175, 80), 3), // Verde quando editando
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            textField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            textField.setText(value != null ? value.toString() : "");
            textField.selectAll();
            return textField;
        }

        @Override
        public Object getCellEditorValue() {
            return textField.getText();
        }

        @Override
        public boolean stopCellEditing() {
            String text = textField.getText().trim();
            try {
                int novaQuantidade = Integer.parseInt(text);
                atualizarQuantidade(currentRow, novaQuantidade);
                return super.stopCellEditing();
            } catch (NumberFormatException e) {
                ItemCarrinho item = carrinhoController.getItens().get(currentRow);
                item.setQuantidade(1);

                JOptionPane.showMessageDialog(CarrinhoView.this,
                        "Por favor, digite um número válido!\n\n" +
                                "A quantidade foi ajustada para 1.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                carregarItens();
                return false;
            }
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnMais;
        private JButton btnMenos;
        private JButton btnRemover;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            setBackground(Color.WHITE);

            btnMais = new JButton("+");
            btnMais.setPreferredSize(new Dimension(45, 30));
            btnMais.setBackground(new Color(76, 175, 80));
            btnMais.setForeground(Color.WHITE);
            btnMais.setFont(new Font("Arial", Font.BOLD, 14));
            btnMais.setFocusPainted(false);

            btnMenos = new JButton("-");
            btnMenos.setPreferredSize(new Dimension(45, 30));
            btnMenos.setBackground(new Color(255, 152, 0));
            btnMenos.setForeground(Color.WHITE);
            btnMenos.setFont(new Font("Arial", Font.BOLD, 14));
            btnMenos.setFocusPainted(false);

            btnRemover = new JButton("X");
            btnRemover.setPreferredSize(new Dimension(45, 30));
            btnRemover.setBackground(new Color(244, 67, 54));
            btnRemover.setForeground(Color.WHITE);
            btnRemover.setFont(new Font("Arial", Font.BOLD, 12));
            btnRemover.setFocusPainted(false);

            add(btnMais);
            add(btnMenos);
            add(btnRemover);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton btnMais;
        private JButton btnMenos;
        private JButton btnRemover;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(Color.WHITE);

            btnMais = new JButton("+");
            btnMais.setPreferredSize(new Dimension(45, 30));
            btnMais.setBackground(new Color(76, 175, 80));
            btnMais.setForeground(Color.WHITE);
            btnMais.setFont(new Font("Arial", Font.BOLD, 14));
            btnMais.setFocusPainted(false);

            btnMenos = new JButton("-");
            btnMenos.setPreferredSize(new Dimension(45, 30));
            btnMenos.setBackground(new Color(255, 152, 0));
            btnMenos.setForeground(Color.WHITE);
            btnMenos.setFont(new Font("Arial", Font.BOLD, 14));
            btnMenos.setFocusPainted(false);

            btnRemover = new JButton("X");
            btnRemover.setPreferredSize(new Dimension(45, 30));
            btnRemover.setBackground(new Color(244, 67, 54));
            btnRemover.setForeground(Color.WHITE);
            btnRemover.setFont(new Font("Arial", Font.BOLD, 12));
            btnRemover.setFocusPainted(false);

            btnMais.addActionListener(e -> {
                fireEditingStopped();
                incrementarItem(currentRow);
            });

            btnMenos.addActionListener(e -> {
                fireEditingStopped();
                decrementarItem(currentRow);
            });

            btnRemover.addActionListener(e -> {
                fireEditingStopped();
                removerItem(currentRow);
            });

            panel.add(btnMais);
            panel.add(btnMenos);
            panel.add(btnRemover);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Ações";
        }
    }
}