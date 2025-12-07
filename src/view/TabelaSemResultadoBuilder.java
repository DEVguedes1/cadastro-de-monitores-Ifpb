package view;

/**
 * Construtor auxiliar de modelos de tabela para visualização de Ofertas de Disciplina.
 * <p>
 * Utilizado na tela de inscrição do aluno ({@link AlunoInscricaoView}).
 * Diferente do builder de resultados, este foca nas <b>metadados</b> da disciplina
 * (Vagas, Pesos) para ajudar o aluno a decidir onde se inscrever.
 */

import javax.swing.table.DefaultTableModel;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class TabelaSemResultadoBuilder {
    
    /**
     * Monta uma tabela de leitura (não editável) com as disciplinas do edital.
     * <p>
     * Colunas geradas:
     * <ul>
     * <li>Nome da Disciplina</li>
     * <li>Quantidade de Vagas</li>
     * <li>Peso da Nota (0.0 - 1.0)</li>
     * <li>Peso do CRE (0.0 - 1.0)</li>
     * </ul>
     * * @param edital O edital contendo a lista de disciplinas ofertadas.
     * @return Um {@link DefaultTableModel} com as células bloqueadas para edição.
     */
    
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