package view;

import javax.swing.table.DefaultTableModel;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class TabelaSemResultadoBuilder {
    
    public static DefaultTableModel montarTabela(Edital edital) {
        // Colunas que interessam ao Aluno antes do resultado
        String[] colunas = {"Disciplina", "Vagas", "Peso Nota", "Peso CRE"};
        
        // Impede edição das células
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (edital.getDisciplinas() != null) {
            for (Disciplina d : edital.getDisciplinas()) {
                modelo.addRow(new Object[]{
                    d.getNomeDisciplina(),
                    d.getQntdVagas(),
                    d.getPesoNota(),
                    d.getPesoCRE()
                });
            }
        }
        return modelo;
    }
}