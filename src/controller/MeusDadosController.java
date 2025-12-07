package controller;

import view.MeusDadosView;
import view.DashboardAluno;
import models.Aluno;
import models.CentralDeInformacoes;
import models.Persistencia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeusDadosController implements ActionListener {

    private MeusDadosView view;
    private Aluno alunoLogado;

    public MeusDadosController(MeusDadosView view, Aluno aluno) {
        this.view = view;
        this.alunoLogado = aluno; // Este objeto veio da memória (Dashboard)

        this.view.addSalvarListener(this);
        this.view.addVoltarListener(e -> voltar());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nome = view.getNome();
        String email = view.getEmail();
        String senha = view.getSenha();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            view.mostrarMensagem("Todos os campos são obrigatórios!");
            return;
        }

        // Recupera a central para garantir persistência correta
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        Aluno alunoBanco = central.recuperarAlunoPorMatricula(alunoLogado.getMatricula());

        if (alunoBanco != null) {
            // Atualiza os dados
            alunoBanco.setNomeDoAluno(nome);
            alunoBanco.setEmail(email);
            alunoBanco.setSenha(senha);

            // Salva no XML
            Persistencia.salvarCentral(central);
            
            view.mostrarMensagem("Dados atualizados com sucesso!");
            
            // Atualiza o objeto local para o nome aparecer certo no Dashboard ao voltar
            this.alunoLogado = alunoBanco; 
            voltar();
        } else {
            view.mostrarMensagem("Erro crítico: Cadastro não encontrado no banco.");
        }
    }

    private void voltar() {
        view.dispose();
        DashboardAluno dash = new DashboardAluno(alunoLogado.getNomeDoAluno());
        dash.setVisible(true);
        new DashboardAlunoController(dash, alunoLogado.getNomeDoAluno());
    }
}