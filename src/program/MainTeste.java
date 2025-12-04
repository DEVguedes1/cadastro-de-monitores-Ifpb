package program;

import view.LoginView;
import view.CadastroCoordenadorView;
import controller.LoginController;
import controller.CadastroCoordController;
import models.utils.UsuarioService;
import javax.swing.JOptionPane;

public class MainTeste {
    public static void main(String[] args) {
        System.out.println("1. Iniciando Main...");

        try {
            System.out.println("2. Testando conexão com o Banco de Dados (Service)...");
            
            // É AQUI QUE O ERRO DEVE ACONTECER SE FALTAR BIBLIOTECA
            UsuarioService service = new UsuarioService(); 
            
            System.out.println("3. Service criado. Verificando coordenador...");
            boolean temChefe = service.existeCoordenador();
            System.out.println("4. Verificação concluída. Resultado: " + temChefe);

            if (temChefe) {
                System.out.println("5. Abrindo Login...");
                LoginView login = new LoginView();
                new LoginController(login);
                login.setVisible(true);
            } else {
                System.out.println("5. Abrindo Cadastro...");
                CadastroCoordenadorView cadastro = new CadastroCoordenadorView();
                new CadastroCoordController(cadastro);
                cadastro.setVisible(true);
            }

        } catch (NoClassDefFoundError e) {
            // ERRO ESPECÍFICO DE BIBLIOTECA FALTANDO
            String erro = "ERRO CRÍTICO: Biblioteca faltando!\n\n" +
                          "O Java não encontrou a biblioteca 'XStream'.\n" +
                          "Você precisa adicionar o arquivo .jar do XStream no projeto.\n\n" +
                          "Detalhe técnico: " + e.getMessage();
            System.err.println(erro);
            JOptionPane.showMessageDialog(null, erro);
            
        } catch (Exception e) {
            // OUTROS ERROS
            String erro = "ERRO AO INICIAR:\n" + e.getMessage();
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, erro);
        }
    }
}