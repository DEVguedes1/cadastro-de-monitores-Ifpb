package controller;

/**
 * Controlador responsável por cadastrar o Coordenador do sistema.
 *
 * <p>Este cadastro ocorre principalmente no primeiro acesso ao sistema,
 * quando ainda não existe nenhum coordenador registrado.
 *
 * Funções:
 * <ul>
 *     <li>Validação dos dados inseridos;</li>
 *     <li>Criação do coordenador;</li>
 *     <li>Persistência no XML via {@link Persistencia};</li>
 *     <li>Retorno para a tela de Login após sucesso.</li>
 * </ul>
 *
 * O fluxo:
 * <ol>
 *     <li>Usuário preenche nome, email e senha;</li>
 *     <li>O sistema valida e adiciona o coordenador;</li>
 *     <li>Redireciona para o login.</li>
 * </ol>
 *
 * @author Seu Nome
 */

import view.CadastroCoordenadorView;
import view.LoginView;
import models.Coordenador;
import models.CentralDeInformacoes;
import models.Persistencia;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCoordController implements ActionListener {
    
    private CadastroCoordenadorView view;

    
    /**
     * Construtor.
     *
     * @param view tela de cadastro do coordenador
     */

    public CadastroCoordController(CadastroCoordenadorView view) {
        this.view = view;
        
        // --- AQUI ESTÁ O SEGREDO ---
        this.view.addAcaoCadastrar(this);
        
        this.view.addVoltarListener(e -> {
            view.dispose();
            LoginView login = new LoginView();
            login.setVisible(true);
            new LoginController(login);
        });
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[DEBUG] Botão clicado!"); // Debug para ver se funcionou
        
        String nome = view.getNome();
        String email = view.getEmail();
        String senha = view.getSenha();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha tudo!");
            return;
        }

        try {
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Coordenador coord = new Coordenador(nome, email, senha);
            
            if(central.adicionarCoordenador(coord)) {
                Persistencia.salvarCentral(central);
                System.out.println("[DEBUG] Salvo no XML com sucesso.");
                
                JOptionPane.showMessageDialog(view, "Sucesso! Faça login.");
                view.dispose();
                
                // Abre o Login
                LoginView login = new LoginView();
                new LoginController(login); // Liga o controller do login
                login.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Erro: Email já existe.");
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Mostra erro no console se falhar ao salvar
            JOptionPane.showMessageDialog(view, "Erro técnico: " + ex.getMessage());
        }
    }
}