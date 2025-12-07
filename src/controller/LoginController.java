package controller;

/**
 * Controlador de Autenticação e Sessão.
 * <p>
 * Ponto de entrada lógico do sistema.
 * Responsabilidades:
 * <ul>
 * <li>Validar campos vazios.</li>
 * <li>Chamar o {@link UsuarioService} para verificar credenciais.</li>
 * <li>Direcionar para o Dashboard correto (Aluno ou Coordenador) dependendo do tipo de usuário.</li>
 * <li>Abrir o fluxo de criação de conta.</li>
 * </ul>
 */

import view.DashboardAluno;
import view.DashboardCoordenador;
import view.LoginView;
import view.TelaEscolhaCadastro; // Import novo
import models.utils.UsuarioService;
import models.UsuarioLogado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginView view;
    private UsuarioService usuarioService;

    public LoginController(LoginView view) {
        this.view = view;
        this.usuarioService = new UsuarioService();
        
        // Listener Entrar
        this.view.addLoginListener(this);
        
        // Listener Criar Conta (NOVO)
        this.view.addCriarContaListener(e -> abrirTelaEscolha());
    }

    private void abrirTelaEscolha() {
        // Abre a janelinha modal por cima do Login
        TelaEscolhaCadastro dialog = new TelaEscolhaCadastro(view);
        new EscolhaCadastroController(dialog, view);
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ... (Seu código de login continua IDÊNTICO aqui) ...
        String email = view.getEmail();
        String senha = view.getSenha();
        
        // Só para garantir que não perdeu a lógica:
        if (email.isEmpty() || senha.isEmpty()) {
             view.mostrarMensagem("Preencha todos os campos!");
             return;
        }
        
        UsuarioLogado usuario = usuarioService.autenticar(email, senha);
        if (usuario != null) {
            view.dispose();
            if (usuario.getTipo().equals("COORDENADOR")) {
                DashboardCoordenador dash = new DashboardCoordenador(usuario.getNome());
                dash.setVisible(true);
                new DashboardCoordController(dash, usuario.getNome());
            } else {
            	DashboardAluno dash = new DashboardAluno(usuario.getNome());
                dash.setVisible(true);
                new DashboardAlunoController(dash, usuario.getNome()); // Liga o controller!
            }
        } else {
            view.mostrarMensagem("Dados incorretos!");
        }
    }
}