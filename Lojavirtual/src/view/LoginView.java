package view;
 
import controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private LoginController controller;

    public LoginView() {
        this.controller = new LoginController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        setTitle("Obscure Street - Login");
        setSize(430, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fundo
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(12, 12, 12)); // preto sujo
        add(painelPrincipal);

        // -------------------- LOGO --------------------
        JLabel lblLogo = new JLabel("", SwingConstants.CENTER);
        lblLogo.setPreferredSize(new Dimension(400, 180));

        try {
            java.net.URL url = getClass().getResource("/assets/logo.png");

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(300, 140, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(img));
            } else {
                lblLogo.setText("OBSCURE STREET");
                lblLogo.setForeground(new Color(190, 0, 0));
                lblLogo.setFont(new Font("Arial", Font.BOLD, 34));
            }

        } catch (Exception e) {
            lblLogo.setText("OBSCURE STREET");
            lblLogo.setForeground(new Color(190, 0, 0));
            lblLogo.setFont(new Font("Arial", Font.BOLD, 34));
        }

        painelPrincipal.add(lblLogo, BorderLayout.NORTH);

        // -------------------- FORMULÁRIO CENTRALIZADO --------------------
        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBackground(new Color(22, 22, 22));
        painelForm.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));

        // TÍTULO LOGIN CENTRALIZADO
        JLabel lblTitulo = new JLabel("LOGIN");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(180, 0, 0));

        painelForm.add(lblTitulo);
        painelForm.add(Box.createRigidArea(new Dimension(0, 30)));


        // -------------------- USUÁRIO --------------------
        JPanel linhaUsuario = new JPanel();
        linhaUsuario.setLayout(new BoxLayout(linhaUsuario, BoxLayout.Y_AXIS));
        linhaUsuario.setBackground(new Color(22, 22, 22));
        linhaUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(400, 35));
        txtUsuario.setBackground(new Color(40, 40, 40));
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(120, 0, 0)));

        linhaUsuario.add(lblUsuario);
        linhaUsuario.add(txtUsuario);

        painelForm.add(linhaUsuario);
        painelForm.add(Box.createRigidArea(new Dimension(0, 20)));


        // -------------------- SENHA --------------------
        JPanel linhaSenha = new JPanel();
        linhaSenha.setLayout(new BoxLayout(linhaSenha, BoxLayout.Y_AXIS));
        linhaSenha.setBackground(new Color(22, 22, 22));
        linhaSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtSenha = new JPasswordField();
        txtSenha.setMaximumSize(new Dimension(400, 35));
        txtSenha.setBackground(new Color(40, 40, 40));
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);
        txtSenha.setBorder(BorderFactory.createLineBorder(new Color(120, 0, 0)));

        linhaSenha.add(lblSenha);
        linhaSenha.add(txtSenha);

        painelForm.add(linhaSenha);
        painelForm.add(Box.createRigidArea(new Dimension(0, 35)));


        // -------------------- BOTÃO LOGIN CENTRALIZADO --------------------
        btnLogin = new JButton("ENTRAR");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setPreferredSize(new Dimension(200, 45));
        btnLogin.setMaximumSize(new Dimension(200, 45));

        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(150, 0, 0));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Hover estilo obscure
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(110, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(150, 0, 0));
            }
        });

        painelForm.add(btnLogin);


        // Adiciona no principal
        painelPrincipal.add(painelForm, BorderLayout.CENTER);

        configurarEventos();

        painelForm.add(btnLogin);
        painelPrincipal.add(painelForm, BorderLayout.CENTER);
    }


    private void configurarEventos() {
        btnLogin.addActionListener(e -> autenticar());
        txtSenha.addActionListener(e -> btnLogin.doClick());
    }

    private void autenticar() {
    String usuario = txtUsuario.getText().trim();
    String senha = new String(txtSenha.getPassword());

    var usuarioLogado = controller.autenticar(usuario, senha);

    if (usuarioLogado != null) {
        JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");

        new TelaInicialView(usuarioLogado).setVisible(true);
        this.dispose();

    } else {
        JOptionPane.showMessageDialog(this,
                "Usuário ou senha incorretos!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}