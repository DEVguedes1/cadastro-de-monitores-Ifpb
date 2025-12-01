package view;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;

public class JanelaPadrao extends JFrame {

    // Cores "Modernas"
    public static final Color COR_FUNDO = new Color(44, 62, 80); // Azul Escuro (Midnight Blue)
    public static final Color COR_TEXTO = new Color(236, 240, 241); // Branco Gelo
    public static final Color COR_BOTAO = new Color(39, 174, 96);   // Verde Flat

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