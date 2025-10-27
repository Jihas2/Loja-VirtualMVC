package view;

import controller.PecaRoupaController;
import model.PecaRoupa;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaView extends JFrame {
    private PecaRoupaController controller;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnVoltar;
    private JButton btnRemover;
    private JButton btnEditar;
    private JButton btnVitrine;

    public ListaView(PecaRoupaController controller) {
        this.controller = controller;
        inicializarComponentes();
        carregarDados();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gerenciamento de Peças - Lista de Exibição");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        JLabel lblTitulo = new JLabel("Peças em Exibição", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(102, 126, 234));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Tipo", "Tamanho", "Cor", "Preço (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(102, 126, 234));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(new Color(245, 245, 250));

        btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(130, 40));
        btnVoltar.setBackground(new Color(102, 126, 234));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEditar = new JButton(" Editar");
        btnEditar.setPreferredSize(new Dimension(130, 40));
        btnEditar.setBackground(new Color(255, 152, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 13));
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRemover = new JButton("Remover");
        btnRemover.setPreferredSize(new Dimension(130, 40));
        btnRemover.setBackground(new Color(244, 67, 54));
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setFont(new Font("Arial", Font.BOLD, 13));
        btnRemover.setFocusPainted(false);
        btnRemover.setBorderPainted(false);
        btnRemover.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVitrine = new JButton("Vitrine");
        btnVitrine.setPreferredSize(new Dimension(130, 40));
        btnVitrine.setBackground(new Color(156, 39, 176));
        btnVitrine.setForeground(Color.WHITE);
        btnVitrine.setFont(new Font("Arial", Font.BOLD, 13));
        btnVitrine.setFocusPainted(false);
        btnVitrine.setBorderPainted(false);
        btnVitrine.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnVitrine);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        configurarEventos();
    }

    private void configurarEventos() {
        btnVoltar.addActionListener(e -> voltarParaCadastro());
        btnRemover.addActionListener(e -> removerPeca());
        btnEditar.addActionListener(e -> editarPeca());
        btnVitrine.addActionListener(e -> abrirVitrine());

        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarPeca();
                }
            }
        });
    }

    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<PecaRoupa> pecas = controller.listarPecas();

        for (PecaRoupa peca : pecas) {
            Object[] linha = {
                    peca.getId(),
                    peca.getNome(),
                    peca.getTipo(),
                    peca.getTamanho(),
                    peca.getCor(),
                    String.format("%.2f", peca.getPreco())
            };
            modeloTabela.addRow(linha);
        }
    }

    private void editarPeca() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma peça para editar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        PecaRoupa peca = controller.buscarPorId(id);

        if (peca != null) {
            EdicaoView edicaoView = new EdicaoView(controller, peca, this);
            edicaoView.setVisible(true);
            this.setVisible(false);
        }
    }

    private void removerPeca() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma peça para remover!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente remover a peça: " + nome + "?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controller.removerPeca(id)) {
                JOptionPane.showMessageDialog(this,
                        "Peça removida com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            }
        }
    }

    private void voltarParaCadastro() {
        CadastroView cadastroView = new CadastroView(controller);
        cadastroView.setVisible(true);
        this.dispose();
    }

    private void abrirVitrine() {
        VitrineView vitrineView = new VitrineView(controller);
        vitrineView.setVisible(true);
        this.dispose();
    }


    public void atualizarLista() {
        carregarDados();
    }
}