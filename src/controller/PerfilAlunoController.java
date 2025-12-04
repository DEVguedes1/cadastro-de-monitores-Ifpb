package controller;

import models.Aluno;
import models.CentralDeInformacoes;
import models.Inscricao;
import models.Persistencia;
import models.recurses.Disciplina;
import models.recurses.Edital;
import view.EditarAlunoDialog;
import view.ListarAlunosView;
import view.PerfilAlunoView;

public class PerfilAlunoController {

    private PerfilAlunoView view;
    private Aluno aluno;
    private String nomeCoordenador;

    public PerfilAlunoController(PerfilAlunoView view, Aluno aluno, String nomeCoord) {
        this.view = view;
        this.aluno = aluno;
        this.nomeCoordenador = nomeCoord;

        carregarHistorico();

        this.view.addVoltarListener(e -> voltar());
        this.view.addEditarListener(e -> {
            // Futura funcionalidade 18
            javax.swing.JOptionPane.showMessageDialog(view, "Edição de perfil será implementada a seguir!");
        });
        
     // Dentro do construtor
        this.view.addEditarListener(e -> {
            // Abre o Dialog
            EditarAlunoDialog dialog = new EditarAlunoDialog(view, aluno);
            
            // Cria o controller do dialog
            // Passamos uma função Lambda () -> { ... } que será executada ao terminar
            new EditarAlunoController(dialog, aluno, () -> {
                // O que acontece quando salvar?
                // Fechamos a tela de perfil atual e abrimos de novo atualizada
                view.dispose();
                PerfilAlunoView novaView = new PerfilAlunoView(aluno);
                novaView.setVisible(true);
                new PerfilAlunoController(novaView, aluno, nomeCoordenador);
            });
            
            dialog.setVisible(true);
        });
    }

    private void carregarHistorico() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        var modelo = view.getModeloHistorico();
        modelo.setRowCount(0);

        // VARREDURA: Procura o aluno em todos os editais
        for (Edital edital : central.getTodosOsEditais()) {
            if (edital.getDisciplinas() != null) {
                for (Disciplina disciplina : edital.getDisciplinas()) {
                    // Busca a inscrição deste aluno específico nesta disciplina
                    Inscricao insc = Disciplina.buscarInscricao(disciplina, aluno.getNomeDoAluno());
                    
                    // Se achou inscrição, adiciona na tabela
                    if (insc != null) {
                        modelo.addRow(new Object[]{
                            edital.getNumEdital(),
                            disciplina.getNomeDisciplina(),
                            String.format("%.2f", insc.getNotaFinal()),
                            insc.getSituacao()
                        });
                    }
                }
            }
        }
    }

    private void voltar() {
        view.dispose();
        ListarAlunosView lista = new ListarAlunosView();
        lista.setVisible(true);
        new ListarAlunosController(lista, nomeCoordenador);
    }
}