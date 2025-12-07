package program;

/**
 * Classe principal responsável por iniciar a aplicação Sismon (Sistema de Monitoria).
 * <p>
 * Esta classe representa o ponto de entrada do sistema e tem como funções:
 * <ul>
 *   <li>Configurar o LookAndFeel de acordo com o sistema operacional;</li>
 *   <li>Verificar se já existe um Coordenador cadastrado no sistema (via XML);</li>
 *   <li>Inicializar a interface apropriada: Login ou Cadastro de Primeiro Acesso;</li>
 *   <li>Gerenciar a inicialização segura da interface gráfica na Event Dispatch Thread (EDT),
 *       conforme recomendação do Swing.</li>
 * </ul>
 *
 * O fluxo geral de execução funciona assim:
 * <ol>
 *   <li>Define o visual gráfico nativo (Windows/Mac/Linux);</li>
 *   <li>Consulta o {@link UsuarioService} para descobrir se há Coordenador cadastrado;</li>
 *   <li>Se existir, abre a tela de Login e ativa o {@link LoginController};</li>
 *   <li>Se não existir, abre a tela de Cadastro inicial do Coordenador e ativa o 
 *       {@link CadastroCoordController};</li>
 *   <li>Em caso de erro crítico, exibe um diálogo informando o problema ao usuário.</li>
 * </ol>
 *
 * @author Seu Nome
 */

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.CadastroCoordController;
import controller.LoginController;
import models.utils.UsuarioService;
import view.CadastroCoordenadorView;
import view.LoginView;

public class Main {
    
    /**
     * Método principal responsável por iniciar a aplicação.
     * <p>
     * Toda a execução da interface gráfica é redirecionada para a Thread
     * apropriada do Swing (<b>Event Dispatch Thread</b>) utilizando
     * {@link SwingUtilities#invokeLater(Runnable)} para garantir segurança e
     * evitar problemas de concorrência.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */

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