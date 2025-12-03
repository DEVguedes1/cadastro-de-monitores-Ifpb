package view;

import javax.swing.table.DefaultTableModel;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class TabelaSemResultadoBuilder {
     public static DefaultTableModel montarTabela(Edital edital) {

        String[] colunas = {"Disciplina", "Docente", "Período"};
        DefaultTableModel modelo = new DefaultTableModel(new Object[0][3], colunas);

        for (Disciplina d : edital.getDisciplinas()) {
            modelo.addRow(new Object[]{
                d.getNomeDisciplina(),
                d.getDocente(),
                d.getPeriodo()
            });
        }

        return modelo;
    }

    public static void atualizarTabela(DefaultTableModel modelo, Edital edital) {
        modelo.setRowCount(0);

        for (Disciplina d : edital.getDisciplinas()) {
            modelo.addRow(new Object[]{
                d.getNomeDisciplina(),
                d.getDocente(),
                d.getPeriodo()
            });
        } 

        modelo.fireTableDataChanged();
    }
}
