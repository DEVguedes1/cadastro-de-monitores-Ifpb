package view;

/**
 * Renderizador Condicional de Células (Tabela).
 * <p>
 * Personaliza a exibição da coluna "Situação" nas tabelas do sistema.
 * Aplica um código de cores semântico para facilitar a leitura rápida:
 * <ul>
 * <li><b>Verde:</b> Aprovado.</li>
 * <li><b>Amarelo:</b> Concorrendo.</li>
 * <li><b>Vermelho:</b> Desistente ou Reprovado.</li>
 * </ul>
 */

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {

    private final int situacaoColumnIndex;

    public StatusCellRenderer(int situacaoColumnIndex) {
        this.situacaoColumnIndex = situacaoColumnIndex;
        setOpaque(true); // necessário para a cor de fundo aparecer
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
 
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Segurança: só pega o valor se o índice for válido
        Object situacaoObj = null;
        if (situacaoColumnIndex >= 0 && situacaoColumnIndex < table.getColumnCount()) {
            situacaoObj = table.getValueAt(row, situacaoColumnIndex);
        }

        String situacao = situacaoObj == null ? "" : situacaoObj.toString();

        if (isSelected) {
            // mantém as cores de seleção do sistema
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        } else {
            if ("CONCORRENDO".equalsIgnoreCase(situacao)) {
                c.setBackground(new Color(255, 255, 150)); // Amarelo Claro
                c.setForeground(Color.BLACK);
            } else if ("DESISTENTE".equalsIgnoreCase(situacao)) {
                c.setBackground(new Color(255, 150, 150)); // vermelho claro
                c.setForeground(Color.BLACK);
            } else if ("APROVADO".equalsIgnoreCase(situacao)) {
                c.setBackground(new Color(180, 255, 180)); // Verde claro
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
        }

        return c;
    }
}
