package view;

import model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaInicialView extends JFrame {

    private Usuario usuarioLogado;

    public TelaInicialView(Usuario usuario) {
        this.usuarioLogado = usuario;
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        setTitle("Obscure Street - Menu Inicial");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(10, 10, 10)); // preto sujo

        // ---------- LOGO ----------
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            java.net.URL url = getClass().getResource("/assets/logo.png");
            if (url == null) {
                throw new RuntimeException("Logo não encontrada");
            }

            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(450, 200, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            lblLogo.setText("OBSCURE STREET");
            lblLogo.setForeground(new Color(194, 0, 0));
            lblLogo.setFont(new Font("Arial", Font.BOLD, 40));
        }

        add(lblLogo, BorderLayout.NORTH);

        // ---------- PAINEL CENTRAL ----------
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(new Color(63, 56, 56));
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + usuarioLogado.getUsuario());
        lblBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 26));
        lblBemVindo.setForeground(new Color(180, 0, 0));

        painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));
        painelCentral.add(lblBemVindo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));

        // ---------- BOTÕES ----------
        JButton btnVitrine = criarBotao("VITRINE", new Color(150, 0, 0));
        JButton btnSair = criarBotao("SAIR", new Color(80, 0, 0));

        // abrir vitrine
        btnVitrine.addActionListener(e -> {
            new VitrineView(new controller.PecaRoupaController(), usuarioLogado).setVisible(true);
            dispose();
        });

        // SAIR
        btnSair.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        painelCentral.add(btnVitrine);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));

        // ---------- BOTÕES DO ADMIN ---------
        if (usuarioLogado.getTipo().equalsIgnoreCase("ADMIN")) {

            // Botão LISTA DE PRODUTOS
            JButton btnLista = criarBotao("LISTA DE PRODUTOS", new Color(90, 0, 150));
            btnLista.addActionListener(e -> {
                new ListaView(new controller.PecaRoupaController(), usuarioLogado).setVisible(true);
                dispose();
            });

            // Botão CADASTRAR PRODUTO
            JButton btnCadastrar = criarBotao("CADASTRAR PRODUTO", new Color(0, 90, 150));
            btnCadastrar.addActionListener(e -> {
                new CadastroView(new controller.PecaRoupaController(), usuarioLogado).setVisible(true);
                dispose();
            });

            painelCentral.add(btnLista);
            painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));
            painelCentral.add(btnCadastrar);
            painelCentral.add(Box.createRigidArea(new Dimension(0, 25)));
        }


        painelCentral.add(btnSair);
        add(painelCentral, BorderLayout.CENTER);
    }

    // Criador de botões estilizados
    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(260, 50));
        btn.setMaximumSize(new Dimension(260, 50));

        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(corFundo);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(corFundo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(corFundo);
            }
        });

        return btn;
    }
}
