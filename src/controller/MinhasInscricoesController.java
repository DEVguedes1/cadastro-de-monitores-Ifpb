package controller;

import view.MinhasInscricoesView;
import view.DashboardAluno;
import models.CentralDeInformacoes;
import models.Persistencia;
import models.Inscricao;
import models.recurses.Disciplina;
import models.recurses.Edital;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class MinhasInscricoesController {

    private MinhasInscricoesView view;
    private String nomeAluno;

    public MinhasInscricoesController(MinhasInscricoesView view, String nomeAluno) {
        this.view = view;
        this.nomeAluno = nomeAluno;

        carregarInscricoes();

        this.view.addVoltarListener(e -> voltar());
        this.view.addDesistirListener(e -> desistirDaVaga());
    }

    private void carregarInscricoes() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        var modelo = view.getModelo();
        modelo.setRowCount(0);

        for (Edital edital : central.getTodosOsEditais()) {
            if (edital.getDisciplinas() != null) {
                for (Disciplina disc : edital.getDisciplinas()) {
                    // Busca se o aluno está inscrito nessa disciplina
                    Inscricao insc = Disciplina.buscarInscricao(disc, nomeAluno);
                    
                    if (insc != null) {
                        modelo.addRow(new Object[]{
                            edital.getId(),             // Coluna 0 (Escondida)
                            edital.getNumEdital(),      // Coluna 1
                            disc.getNomeDisciplina(),   // Coluna 2
                            String.format("%.2f", insc.getNotaFinal()), // Coluna 3
                            insc.getSituacao().toString() // Coluna 4 (Colorida)
                        });
                    }
                }
            }
        }
    }

    private void desistirDaVaga() {
        Long idEdital = view.getIdEditalSelecionado();
        String nomeDisc = view.getDisciplinaSelecionada();

        if (idEdital == null || nomeDisc == null) {
            view.mostrarMensagem("Selecione uma inscrição na tabela!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "Tem certeza que deseja DESISTIR desta monitoria?\nEssa ação não pode ser desfeita.",
            "Confirmar Desistência", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Edital edital = central.recuperarEditalPorId(idEdital);
            Disciplina disciplina = edital.buscarDisciplina(nomeDisc);
            Inscricao inscricao = Disciplina.buscarInscricao(disciplina, nomeAluno);

            if (inscricao != null) {
                // Muda o status para DESISTENTE
                inscricao.setSituacao(models.Inscricao.Situacao.DESISTENTE);
                
                // Salva
                Persistencia.salvarCentral(central);
                
                view.mostrarMensagem("Você desistiu da vaga com sucesso.");
                carregarInscricoes(); // Atualiza a tabela
            }
        }
    }

    private void voltar() {
        view.dispose();
        DashboardAluno dash = new DashboardAluno(nomeAluno);
        dash.setVisible(true);
        new DashboardAlunoController(dash, nomeAluno);
    }
}