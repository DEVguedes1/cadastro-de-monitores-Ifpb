package controller;

/**
 * Controlador do Painel Principal do Aluno.
 * <p>
 * Gerencia a navegação e as ações disponíveis para o estudante logado:
 * <ul>
 * <li>Visualizar editais abertos.</li>
 * <li>Acompanhar status das inscrições.</li>
 * <li>Editar dados cadastrais (Perfil).</li>
 * <li>Realizar logout.</li>
 * </ul>
 * * @author Seu Nome
 */

import view.DashboardAluno;
import view.ListarEditaisAlunoView; // Vamos criar a seguir
import view.LoginView;
import view.MeusDadosView;
import view.MinhasInscricoesView;

public class DashboardAlunoController {

    private DashboardAluno view;
    private String nomeAluno;

    public DashboardAlunoController(DashboardAluno view, String nome) {
        this.view = view;
        this.nomeAluno = nome;

        // Ação: Ver Editais
        this.view.addVerEditaisListener(e -> {
            view.dispose();
            ListarEditaisAlunoView listaView = new ListarEditaisAlunoView();
            listaView.setVisible(true);
            new ListarEditaisAlunoController(listaView, nomeAluno); // Vamos criar a seguir
        });

        // Ação: Minhas Inscrições
        this.view.addMinhasInscricoesListener(e -> {
            view.dispose();
            MinhasInscricoesView minView = new MinhasInscricoesView();
            minView.setVisible(true);
            new MinhasInscricoesController(minView, nomeAluno);
        });

        // Ação: Sair
        this.view.addSairListener(e -> {
            view.dispose();
            LoginView login = new LoginView();
            login.setVisible(true);
            new LoginController(login);
        });

        // Ação: alterar dados
        this.view.addMeusDadosListener(e -> {
            // Precisamos recuperar o objeto Aluno completo, pois só temos o nome (String) aqui.
            // Isso é uma pequena limitação da nossa arquitetura atual, mas resolvemos fácil:
            
            models.CentralDeInformacoes central = models.Persistencia.recuperarCentral();
            models.Aluno alunoCompleto = null;
            
            // Busca pelo nome (O ideal seria passarmos a matrícula/email desde o login, mas vamos pelo nome)
            for(models.Aluno a : central.getTodosOsAlunos()) {
                if(a.getNomeDoAluno().equalsIgnoreCase(nomeAluno)) {
                    alunoCompleto = a;
                    break;
                }
            }
            
            if(alunoCompleto != null) {
                view.dispose();
                MeusDadosView dadosView = new MeusDadosView(alunoCompleto);
                dadosView.setVisible(true);
                new MeusDadosController(dadosView, alunoCompleto);
            } else {
                javax.swing.JOptionPane.showMessageDialog(view, "Erro ao carregar seus dados.");
            }
        });
    }
}