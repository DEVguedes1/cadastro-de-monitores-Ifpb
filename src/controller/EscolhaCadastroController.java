package controller;

/**
 * Controlador de fluxo para criação de novas contas.
 * <p>
 * Gerencia o pequeno diálogo modal que aparece na tela de Login.
 * Decide qual tela de cadastro abrir (Aluno ou Coordenador) com base na escolha do usuário
 * e fecha a janela de login anterior.
 */

import view.TelaEscolhaCadastro;
import view.CadastroAlunoView;
import view.CadastroCoordenadorView;
import view.LoginView;

public class EscolhaCadastroController {

    private TelaEscolhaCadastro viewDialog;
    private LoginView viewLogin; // Precisamos referência do login para fechá-lo

    public EscolhaCadastroController(TelaEscolhaCadastro viewDialog, LoginView viewLogin) {
        this.viewDialog = viewDialog;
        this.viewLogin = viewLogin;

        // AÇÃO: ESCOLHEU ALUNO
        this.viewDialog.addAcaoAluno(e -> {
            viewDialog.dispose();
            viewLogin.dispose();
            
            // Tem que criar a View nova aqui
            CadastroAlunoView cadAluno = new CadastroAlunoView(); 
            cadAluno.setVisible(true);
            new CadastroAlunoController(cadAluno);
        });

        // AÇÃO: ESCOLHEU COORDENADOR
        this.viewDialog.addAcaoCoordenador(e -> {
            viewDialog.dispose();
            viewLogin.dispose();

            CadastroCoordenadorView cadCoord = new CadastroCoordenadorView();
            cadCoord.setVisible(true);
            new CadastroCoordController(cadCoord);
        });
    }
}