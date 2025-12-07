package view.style;

/**
 * Utilitários avançados de Interface do Usuário (UI Utils).
 * <p>
 * Contém métodos estáticos para manipulação profunda de componentes Swing,
 * sobrescrevendo renderizadores padrões e aplicando comportamentos de "Flat Design"
 * (Design Plano) que não são nativos do Java Swing.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class UIUtils {

    // Cores modernas
    public static final Color PRIMARY_COLOR = new Color(102, 51, 153); // Seu roxo da sidebar
    public static final Color TABLE_HEADER_BG = new Color(240, 240, 240); // Cinza claro para o cabeçalho
    public static final Color TABLE_HEADER_FG = new Color(60, 60, 60); // Texto escuro
    public static final Color TABLE_SELECTION_BG = new Color(220, 210, 240); // Roxo claro para seleção

    /**
     * Aplica uma estilização moderna e robusta a uma JTable.
     * <p>
     * Diferente do método em {@link Componentes}, este método força a opacidade
     * do cabeçalho e redefine os Renderers para garantir que a cor de fundo (Roxo)
     * apareça corretamente em todos os sistemas operacionais (Windows/Mac/Linux).
     * * @param tabela A tabela a ser transformada.
     */

    public static void styleTable(JTable tabela) {
        // Configurações Gerais
        tabela.setRowHeight(35);
        tabela.setShowVerticalLines(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setSelectionBackground(new Color(220, 210, 240)); // Roxo claro
        tabela.setSelectionForeground(Color.BLACK);
        
        // CORREÇÃO: Remove o foco da célula que fica feio
        tabela.setFocusable(false);

        // CORREÇÃO: Força o cabeçalho a ser Roxo
        JTableHeader header = tabela.getTableHeader();
        header.setOpaque(true); // <--- IMPORTANTE
        header.setBackground(new Color(110, 100, 240)); // Roxo Sidebar
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Renderizador do Cabeçalho (Para garantir alinhamento e cor em todos os OS)
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(110, 100, 240)); // ROXO
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                return label;
            }
        });

        // Centralizar Células de Dados
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.setDefaultRenderer(Object.class, centerRenderer);
    }
    

    /**
     * Aplica o estilo "Flat Button" (Botão Plano) a um JButton.
     * <p>
     * Remove todos os efeitos 3D, bordas de foco e gradientes nativos do Swing.
     * Adiciona interatividade moderna:
     * <ul>
     * <li>Cursor de "Mãozinha" (Hand Cursor) ao passar o mouse.</li>
     * <li>Efeito de "Hover" (escurecimento leve) ao passar o mouse.</li>
     * </ul>
     * * @param btn O botão a ser estilizado.
     * @param corFundo A cor sólida principal do botão.
     */

    public static void styleButton(JButton btn, Color corFundo) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(corFundo);
        btn.setForeground(Color.WHITE);
        
        // O Segredo do Flat Design no Java Swing:
        btn.setFocusPainted(false); // Tira a linha pontilhada de foco
        btn.setBorderPainted(false); // Tira a borda 3D/Sombreada
        btn.setContentAreaFilled(true); // Garante que a cor de fundo apareça
        btn.setOpaque(true); 
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Mãozinha ao passar o mouse

        // Efeito Hover (Opcional: escurece quando passa o mouse)
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if(btn.isEnabled()) btn.setBackground(corFundo.darker());
            }
            public void mouseExited(MouseEvent e) {
                if(btn.isEnabled()) btn.setBackground(corFundo);
            }
        });
    }
}
