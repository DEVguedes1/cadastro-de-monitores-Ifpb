package view.style;

/**
 * Fábrica de Componentes (Factory Pattern) para a interface gráfica.
 * <p>
 * Responsável por instanciar e configurar componentes Swing (Botões, Labels)
 * com as fontes e estilos padrões do sistema, evitando repetição de código nas Views.
 */

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
    /** Fonte padrão para Títulos de páginas (24px, Negrito). */
    public static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    
    /** Fonte padrão para subtítulos e rótulos de campos (14px, Normal). */
    public static final Font FONT_SUB = new Font("Segoe UI", Font.PLAIN, 14);
    
    /** Fonte padrão para textos internos de botões (14px, Negrito). */
    public static final Font FONT_BOTAO = new Font("Segoe UI", Font.BOLD, 14);

    /**
     * Cria um botão estilizado para o Menu Lateral (Sidebar).
     * <p>
     * O botão possui fundo transparente (ou da cor da sidebar), texto branco,
     * alinhamento à esquerda e sem bordas visíveis.
     * * @param texto O texto a ser exibido no botão.
     * @return Um {@link JButton} configurado para a sidebar.
     */

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

    /**
     * Cria um "Card" de Dashboard (Botão grande com Título e Valor).
     * <p>
     * Utiliza HTML básico para formatar o texto em duas linhas (Título menor, Valor maior).
     * * @param titulo O rótulo do card (ex: "Alunos").
     * @param valor O valor ou ícone de destaque (ex: "150" ou "+").
     * @param cor A cor de fundo do card (ex: {@link Cores#VERDE}).
     * @return Um {@link JButton} formatado como um card informativo.
     */

    public static JButton criarBotaoCard(String titulo, String valor, Color cor) {
        JButton btn = new JButton("<html><center><br>" + titulo + "<br><br><font size=6>" + valor + "</font></center></html>");
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    /**
     * Aplica o estilo visual padrão a uma tabela (JTable).
     * <p>
     * <b>Configurações aplicadas:</b>
     * <ul>
     * <li>Altura da linha aumentada para 35px.</li>
     * <li>Remoção das linhas de grade verticais.</li>
     * <li>Cabeçalho com fundo Roxo e fonte Branca.</li>
     * <li>Centralização do conteúdo das células.</li>
     * </ul>
     * * @param tabela A instância de {@link JTable} a ser estilizada.
     */

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
        
    }
}