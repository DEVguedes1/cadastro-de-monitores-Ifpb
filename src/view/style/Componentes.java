package view.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Componentes {

    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUB = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOTAO = new Font("Segoe UI", Font.BOLD, 14);

    // Cria um botão estilo "Link de Sidebar"
    public static JButton criarBotaoSidebar(String texto) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Cores.ROXO_SIDEBAR);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorder(new EmptyBorder(10, 20, 10, 0)); // Padding
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    // Cria um "Card" (Botão grande e colorido igual sua referência)
    public static JButton criarBotaoCard(String titulo, String valor, Color cor) {
        JButton btn = new JButton("<html><center><br>" + titulo + "<br><br><font size=6>" + valor + "</font></center></html>");
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    // Estiliza a Tabela
    public static void estilizarTabela(JTable tabela) {
        // 1. Configurações da Tabela (SEGURO)
        tabela.setRowHeight(35); 
        tabela.setShowVerticalLines(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Cores de Seleção
        tabela.setSelectionBackground(new Color(220, 210, 240)); // Roxo claro
        tabela.setSelectionForeground(Color.BLACK);

        // 2. Cabeçalho (Header)
        JTableHeader header = tabela.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Cores.ROXO_SIDEBAR); // Usando sua cor Roxa
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        // 3. Centralizar Células
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.setDefaultRenderer(Object.class, centerRenderer);
        
        // --- REMOVIDO O BLOCO "table.getParent()" QUE DAVA ERRO ---
        // A estilização do ScrollPane já está sendo feita nas Views (ListarEditaisView, etc).
    }
}