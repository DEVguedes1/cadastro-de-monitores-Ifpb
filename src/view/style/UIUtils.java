package view.style;

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

    public static void styleTable(JTable table) {
        // 1. Básico da Tabela
        table.setRowHeight(35); // Aumenta a altura da linha (IMPORTANTE para UX)
        table.setShowVerticalLines(false); // Remove linhas verticais
        table.setIntercellSpacing(new Dimension(0, 0)); // Remove espaçamento entre celulas
        table.setSelectionBackground(TABLE_SELECTION_BG);
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Fonte moderna

        // Remove a borda do JScrollPane que envolve a tabela
        if (table.getParent() instanceof JViewport) {
            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(Color.WHITE);
        }

        // 2. Estilizando o Cabeçalho (JTableHeader)
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(TABLE_HEADER_BG);
                label.setForeground(TABLE_HEADER_FG);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Apenas linha inferior
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        // 3. Centralizando o conteúdo das células
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
 // --- NOVO: ESTILIZADOR DE BOTÕES (MÁGICA FLAT) ---
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
