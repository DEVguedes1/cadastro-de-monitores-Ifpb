package view;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.Inscricao;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class TabelaResultadoBuilder {
    public static DefaultTableModel montarTabela(Edital edital) {
        String[] colunas = {"Nome do aluno", "Disciplina", "Nota", ""};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Disciplina d : edital.getDisciplinas()) {
            ArrayList<Inscricao> inscricoes = new ArrayList<>(d.getInscricoes());
            inscricoes.sort((a, b) -> Double.compare(b.getNotaFinal(), a.getNotaFinal()));

            for (Inscricao i : inscricoes) {
                modelo.addRow(new Object[]{
                    i.getAluno().getNomeDoAluno(),
                    i.getDisciplina().getNomeDisciplina(),
                    i.getNotaFinal(),
                    i.getSituacao()
                });
            }
        }

        return modelo;
    }
    public static void atualizarTabela(DefaultTableModel modelo, Edital edital) {
        modelo.setRowCount(0); // limpa

        for (Disciplina d : edital.getDisciplinas()) {
            ArrayList<Inscricao> inscricoes = new ArrayList<>(d.getInscricoes());
            inscricoes.sort((a, b) -> Double.compare(b.getNotaFinal(), a.getNotaFinal()));

            for (Inscricao i : inscricoes) {
                modelo.addRow(new Object[]{
                    i.getAluno().getNomeDoAluno(),
                    i.getDisciplina().getNomeDisciplina(),
                    i.getNotaFinal(),
                    i.getSituacao()
                });
            }
        }
        modelo.fireTableDataChanged();
    } 
}
