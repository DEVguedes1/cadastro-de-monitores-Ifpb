package program;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.CadastroCoordController;
import controller.LoginController;
import models.utils.UsuarioService;
import view.CadastroCoordenadorView;
import view.LoginView;

public class Main {

    public static void main(String[] args) {
        // Roda a interface na Thread correta do Swing (Boas práticas)
        SwingUtilities.invokeLater(() -> {
            try {
                // Aplica o visual nativo do sistema operacional (Windows/Linux/Mac)
                // Isso deixa a barra de título e as bordas mais bonitas
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Verifica o estado do sistema
                UsuarioService service = new UsuarioService();

                if (service.existeCoordenador()) {
                    // FLUXO NORMAL: Já existe chefe, abre o Login
                    System.out.println("[Sistema] Iniciando tela de Login...");
                    LoginView login = new LoginView();
                    login.setVisible(true);
                    new LoginController(login);
                } else {
                    // FLUXO INICIAL: Sistema vazio, obriga cadastro do Chefe
                    System.out.println("[Sistema] Primeiro acesso detectado. Iniciando configuração.");
                    CadastroCoordenadorView cadastro = new CadastroCoordenadorView();
                    cadastro.setVisible(true);
                    
                    // Nota: O controller do cadastro já tem a lógica para abrir o Login depois
                    new CadastroCoordController(cadastro); 
                }

            } catch (Exception e) {
                System.err.println("Erro crítico ao iniciar o sistema:");
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar: " + e.getMessage() + "\nVerifique as bibliotecas (XStream).");
            }
        });
    }
}