package view;

import controller.PecaRoupaController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;

public class CadastroView extends JFrame {
    private PecaRoupaController controller;

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
    private JButton btnLimpar;
    private JButton btnVerLista;
    private Usuario usuarioLogado;

    public CadastroView(PecaRoupaController controller, Usuario usuario) {
        this.controller = controller;
        this.usuarioLogado = usuario;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gerenciamento de Peças - Cadastro");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal com padding
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Peças de Roupa", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(102, 126, 234));
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
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.3;
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

        // Descrição
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.3;
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

        // Estoque (NOVO CAMPO)
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblEstoque = new JLabel("Estoque:*");
        lblEstoque.setFont(new Font("Arial", Font.BOLD, 12));
        painelForm.add(lblEstoque, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtEstoque = new JTextField(20);
        txtEstoque.setPreferredSize(new Dimension(250, 30));
        painelForm.add(txtEstoque, gbc);

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

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(new Color(245, 245, 250));

        btnSalvar = new JButton("Salvar Peça");
        btnSalvar.setPreferredSize(new Dimension(150, 40));
        btnSalvar.setBackground(new Color(102, 126, 234));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLimpar = new JButton("Limpar");
        btnLimpar.setPreferredSize(new Dimension(120, 40));
        btnLimpar.setBackground(new Color(224, 224, 224));
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 13));
        btnLimpar.setFocusPainted(false);
        btnLimpar.setBorderPainted(false);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVerLista = new JButton("Ver Lista");
        btnVerLista.setPreferredSize(new Dimension(120, 40));
        btnVerLista.setBackground(new Color(76, 175, 80));
        btnVerLista.setForeground(Color.WHITE);
        btnVerLista.setFont(new Font("Arial", Font.BOLD, 13));
        btnVerLista.setFocusPainted(false);
        btnVerLista.setBorderPainted(false);
        btnVerLista.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVerLista);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        configurarEventos();
    }

    private void configurarEventos() {
        btnSalvar.addActionListener(e -> salvarPeca());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVerLista.addActionListener(e -> abrirListaView());
        btnEscolherImagem.addActionListener(e -> escolherImagem());
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

    private void salvarPeca() {
        try {
            String nome = txtNome.getText().trim();
            String tipo = (String) cmbTipo.getSelectedItem();
            String tamanho = (String) cmbTamanho.getSelectedItem();
            String cor = txtCor.getText().trim();
            String precoStr = txtPreco.getText().trim().replace(",", ".");
            String descricao = txtDescricao.getText().trim();
            String caminhoImagem = txtCaminhoImagem.getText().trim();
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
                //throw new IllegalArgumentException("Selecione um tamanho válido.");
                tamanho = "";
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

            //se passou por todas verificações, joga no controller
            controller.adicionarPeca(nome, tipo, tamanho, cor, preco, descricao, caminhoImagem, estoque);

            JOptionPane.showMessageDialog(
                    this,
                    "Peça cadastrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limparCampos();


        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void limparCampos() {
        txtNome.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbTamanho.setSelectedIndex(0);
        txtCor.setText("");
        txtPreco.setText("");
        txtDescricao.setText("");
        txtCaminhoImagem.setText("");
        txtNome.requestFocus();
        txtEstoque.setText("");

    }

    private void abrirListaView() {
        ListaView listaView = new ListaView(controller, usuarioLogado);
        listaView.setVisible(true);
        this.dispose();
    }
}