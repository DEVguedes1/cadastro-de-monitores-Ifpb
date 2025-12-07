package view;

/**
 * Construtor auxiliar de modelos de tabela (TableModel) para exibição de Rankings.
 * <p>
 * Responsável por transformar os dados complexos de {@link Edital}, {@link Disciplina}
 * e {@link Inscricao} em linhas simples para exibição na JTable de resultados.
 * <p>
 * Aplica regras de visualização como:
 * <ul>
 * <li>Ordenação dos alunos pela maior nota final.</li>
 * <li>Separação visual de desistentes (movidos para o final da lista).</li>
 * </ul>
 */

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.Inscricao;
import models.Inscricao.Situacao;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class TabelaResultadoBuilder {

    /**
     * Constrói o modelo inicial da tabela de resultados.
     * <p>
     * Cria as colunas "Nome", "Disciplina", "Nota" e "Situação" e popula
     * com todos os inscritos, ordenados por nota decrescente.
     * * @param edital O edital cujos resultados serão exibidos.
     * @return Um {@link DefaultTableModel} pronto para uso na JTable.
     */

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

    /**
     * Atualiza um modelo existente com dados novos, mantendo a estrutura.
     * <p>
     * <b>Lógica de Ordenação:</b>
     * 1. Primeiro, lista todos os alunos ativos (Concorrendo, Aprovados, Espera) ordenados por nota.
     * 2. Em seguida, lista os alunos com status {@code DESISTENTE} no final da tabela.
     * <p>
     * Isso garante que desistências não atrapalhem a visualização do ranking oficial.
     * * @param modelo O modelo da tabela a ser limpo e repopulado.
     * @param edital O edital contendo os dados atualizados.
     */
    
    public static void atualizarTabela(DefaultTableModel modelo, Edital edital) {
        modelo.setRowCount(0);

        for (Disciplina d : edital.getDisciplinas()) {
            ArrayList<Inscricao> inscricoes = new ArrayList<>(d.getInscricoes());
            inscricoes.sort((a, b) -> Double.compare(b.getNotaFinal(), a.getNotaFinal()));

            for (Inscricao i : inscricoes) {
                if (!i.getSituacao().equals(Situacao.DESISTENTE)) {
                    modelo.addRow(new Object[]{
                            i.getAluno().getNomeDoAluno(),
                            i.getDisciplina().getNomeDisciplina(),
                            i.getNotaFinal(),
                            i.getSituacao()
                    });
                }
            }

            for (Inscricao i : inscricoes) {
                if (i.getSituacao().equals(Situacao.DESISTENTE)) {
                    modelo.addRow(new Object[]{
                            i.getAluno().getNomeDoAluno(),
                            i.getDisciplina().getNomeDisciplina(),
                            i.getNotaFinal(),
                            i.getSituacao()
                    });
                }
            }
        }

        modelo.fireTableDataChanged();
    }
}
