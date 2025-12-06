package view;

import controller.PecaRoupaController;
import model.PecaRoupa;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;

public class EdicaoView extends JFrame {
    private PecaRoupaController controller;
    private PecaRoupa pecaEditando;
    private ListaView listaView;

    private JTextField txtNome;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbTamanho;
    private JTextField txtCor;
    private JTextField txtPreco;
    private JTextArea txtDescricao;
    private JTextField txtEstoque;
    private JTextField txtCaminhoImagem;
    private JButton btnEscolherImagem;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public EdicaoView(PecaRoupaController controller, PecaRoupa peca, ListaView listaView) {
        this.controller = controller;
        this.pecaEditando = peca;
        this.listaView = listaView;
        inicializarComponentes();
        preencherCampos();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gerenciamento de Peças - Edição");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        JLabel lblTitulo = new JLabel("Editar Peça de Roupa", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(255, 152, 0));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Painel do formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.WHITE);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblNome = new JLabel("Nome da Peça:*");
        lblNome.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblNome, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtNome = new JTextField(20);
        txtNome.setPreferredSize(new Dimension(250, 30));
        painelForm.add(txtNome, gbc);

        // Tipo
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lblTipo = new JLabel("Tipo:*");
        lblTipo.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblTipo, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        String[] tipos = {"Selecione", "Camiseta", "Camisa", "Calça", "Shorts",
                "Vestido", "Saia", "Jaqueta", "Casaco", "Blusa", "Outros"};
        cmbTipo = new JComboBox<>(tipos);
        cmbTipo.setPreferredSize(new Dimension(250, 30));
        painelForm.add(cmbTipo, gbc);

        // Tamanho
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel lblTamanho = new JLabel("Tamanho:*");
        lblTamanho.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblTamanho, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        String[] tamanhos = {"Selecione", "PP", "P", "M", "G", "GG", "XG"};
        cmbTamanho = new JComboBox<>(tamanhos);
        cmbTamanho.setPreferredSize(new Dimension(250, 30));
        painelForm.add(cmbTamanho, gbc);

        // Cor
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        JLabel lblCor = new JLabel("Cor:");
        lblCor.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblCor, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtCor = new JTextField(20);
        txtCor.setPreferredSize(new Dimension(250, 30));
        painelForm.add(txtCor, gbc);

        // Preço
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        JLabel lblPreco = new JLabel("Preço (R$):*");
        lblPreco.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblPreco, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtPreco = new JTextField(20);
        txtPreco.setPreferredSize(new Dimension(250, 30));
        painelForm.add(txtPreco, gbc);

        // Estoque
        gbc.gridx = 0; 
        gbc.gridy = 5;        // Aumentar todos os próximos +1 (Descrição vira 6, Imagem vira 7...)
        gbc.weightx = 0.3;

        JLabel lblEstoque = new JLabel("Estoque:*");
        lblEstoque.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblEstoque, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtEstoque = new JTextField(20);
        txtEstoque.setPreferredSize(new Dimension(250, 30));
        painelForm.add(txtEstoque, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblDescricao, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        txtDescricao = new JTextArea(4, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setPreferredSize(new Dimension(250, 80));
        painelForm.add(scrollDescricao, gbc);

        // Imagem
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel lblImagem = new JLabel("Imagem:");
        lblImagem.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblImagem, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel painelImagem = new JPanel(new BorderLayout(5, 0));
        painelImagem.setBackground(Color.WHITE);
        painelImagem.setPreferredSize(new Dimension(250, 30));

        txtCaminhoImagem = new JTextField();
        txtCaminhoImagem.setPreferredSize(new Dimension(150, 30));
        txtCaminhoImagem.setEditable(false);

        btnEscolherImagem = new JButton("Escolher");
        btnEscolherImagem.setPreferredSize(new Dimension(95, 30));
        btnEscolherImagem.setBackground(new Color(200, 200, 200));
        btnEscolherImagem.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEscolherImagem.setFocusPainted(false);
        btnEscolherImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelImagem.add(txtCaminhoImagem, BorderLayout.CENTER);
        painelImagem.add(btnEscolherImagem, BorderLayout.EAST);
        painelForm.add(painelImagem, gbc);

        painelPrincipal.add(painelForm, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(new Color(245, 245, 250));

        btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setPreferredSize(new Dimension(180, 40));
        btnSalvar.setBackground(new Color(76, 175, 80));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(130, 40));
        btnCancelar.setBackground(new Color(224, 224, 224));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        configurarEventos();
    }

    private void configurarEventos() {
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> cancelar());
        btnEscolherImagem.addActionListener(e -> escolherImagem());

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                cancelar();
            }
        });
    }

    private void escolherImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar Imagem da Peça");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                        name.endsWith(".png") || name.endsWith(".gif");
            }
            public String getDescription() {
                return "Imagens (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtCaminhoImagem.setText(selectedFile.getAbsolutePath());
        }
    }

    private void preencherCampos() {
        txtNome.setText(pecaEditando.getNome());
        cmbTipo.setSelectedItem(pecaEditando.getTipo());
        cmbTamanho.setSelectedItem(pecaEditando.getTamanho());
        txtCor.setText(pecaEditando.getCor());
        txtPreco.setText(pecaEditando.getPreco().toString());
        txtDescricao.setText(pecaEditando.getDescricao() != null ? pecaEditando.getDescricao() : "");
        txtCaminhoImagem.setText(pecaEditando.getImagemBase64() != null ? pecaEditando.getImagemBase64() : "");
    }

    private void salvarAlteracoes() {
        try {
            String nome = txtNome.getText().trim();
            String tipo = (String) cmbTipo.getSelectedItem();
            String tamanho = (String) cmbTamanho.getSelectedItem();
            String cor = txtCor.getText().trim();
            String precoStr = txtPreco.getText().trim().replace(",", ".");
            String descricao = txtDescricao.getText().trim();
            String caminhoImagem = txtCaminhoImagem.getText().trim();
            String imagemBase64 = null;
            String estoqueStr = txtEstoque.getText().trim();

            // Verificações de valores validos.
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("O nome da peça não pode estar vazio.");
            }
            // Tipo
            if (tipo.equals("Selecione")) {
                throw new IllegalArgumentException("Selecione um tipo válido.");
            }
            // Tamanho
            if (tamanho.equals("Selecione")) {
                throw new IllegalArgumentException("Selecione um tamanho válido.");
            }
            // Preço
            BigDecimal preco;
            try {
                preco = new BigDecimal(precoStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Preço inválido. Use apenas números. Ex: 49.90");
            }
            if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O preço deve ser maior que zero.");
            }
            //imagem
            if (!caminhoImagem.isEmpty()) {
                imagemBase64 = converterImagemParaBase64(caminhoImagem);
            }
            // Estoque
            int estoque;
            try {
                estoque = Integer.parseInt(estoqueStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O estoque deve ser um número inteiro.");
            }

            if (estoque < 0) {
                throw new IllegalArgumentException("O estoque não pode ser negativo.");
            }

            //se passou por todas verificações de valores válidos, atualiza peça
            controller.atualizarPeca(pecaEditando.getId(), nome, tipo, tamanho, cor, preco, descricao, imagemBase64, estoque);

                JOptionPane.showMessageDialog(this,
                        "Peça atualizada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                listaView.atualizarLista();
                listaView.setVisible(true);
                this.dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String converterImagemParaBase64(String caminho) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(caminho);
            byte[] bytes = java.nio.file.Files.readAllBytes(path);
            return java.util.Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cancelar() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja cancelar a edição? As alterações não serão salvas.",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            listaView.setVisible(true);
            this.dispose();
        }
    }
}