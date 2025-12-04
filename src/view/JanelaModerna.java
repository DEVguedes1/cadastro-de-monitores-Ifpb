package view;

import javax.swing.*;
import view.style.Cores;
import java.awt.*;

public class JanelaModerna extends JFrame {

    protected JPanel painelSidebar;
    protected JPanel painelConteudo;

    public JanelaModerna(String titulo) {
        setTitle(titulo);
        setSize(1000, 650); // Tela Widescreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null); // Vamos manter null para você posicionar coisas manualmente se quiser

        // 1. Sidebar (Menu Lateral Roxo)
        painelSidebar = new JPanel();
        painelSidebar.setBackground(Cores.ROXO_SIDEBAR);
        painelSidebar.setLayout(null);
        painelSidebar.setBounds(0, 0, 220, 650);
        add(painelSidebar);

    
        // 2. Painel de Conteúdo (Branco)
        painelConteudo = new JPanel();
        painelConteudo.setBackground(Cores.FUNDO_CLARO);
        painelConteudo.setLayout(null);
        painelConteudo.setBounds(220, 0, 780, 650);
        add(painelConteudo);
    }
    
    // Método auxiliar para adicionar botões no menu lateral automaticamente
    protected void adicionarBotaoMenu(JButton btn, int index) {
        btn.setBounds(0, 180 + (index * 55), 220, 50); // Empilha os botões
        painelSidebar.add(btn);
    }
}