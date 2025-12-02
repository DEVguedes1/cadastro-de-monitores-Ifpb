package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JFrame;

public class JanelaPadrao extends JFrame {

    // Cores "Modernas"
    public static final Color COR_FUNDO = new Color(245, 245, 245); 
    public static final Color COR_TEXTO = new Color(0, 0, 0); 
    public static final Color COR_BOTAO = new Color(52, 152, 219);  
    public static final Font fontPadrao = new Font("Arial", Font.BOLD, 18);

    public JanelaPadrao(String titulo, int largura, int altura) {
        setTitle(titulo);
        setSize(largura, altura);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // Aplica a cor de fundo moderna
        Container contentPane = getContentPane();
        contentPane.setBackground(COR_FUNDO);
    }
}
