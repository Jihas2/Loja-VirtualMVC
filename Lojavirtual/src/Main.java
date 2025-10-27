package main;

import controller.PecaRoupaController;
import view.CadastroView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializar o sistema
        SwingUtilities.invokeLater(() -> {
            PecaRoupaController controller = new PecaRoupaController();
            CadastroView cadastroView = new CadastroView(controller);
            cadastroView.setVisible(true);
        });
    }
}
