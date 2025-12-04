package view;

import javax.swing.*;
import view.style.Cores;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaModerna extends JFrame {

    protected JPanel painelSidebar;
    protected JPanel painelConteudo;

    public JanelaModerna(String titulo) {
        setTitle(titulo);
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // 1. Sidebar (Menu Lateral)
        painelSidebar = new JPanel();
        painelSidebar.setBackground(view.style.Cores.ROXO_SIDEBAR);
        painelSidebar.setLayout(null);
        painelSidebar.setBounds(0, 0, 220, 650);
        add(painelSidebar);

        // 2. Painel de Conteúdo
        painelConteudo = new JPanel();
        painelConteudo.setBackground(view.style.Cores.FUNDO_CLARO);
        painelConteudo.setLayout(null);
        painelConteudo.setBounds(220, 0, 780, 650);
        add(painelConteudo);

        // --- APAGUE O JLABEL "USER" QUE ESTAVA AQUI ---
        
        // Mantém apenas o menu lateral
        adicionarMenuLateral(); 
    }

    // --- MÉTODOS DE CONFIGURAÇÃO VISUAL ---

    // Método para desenhar o perfil (Chamado pelas subclasses Dashboard)
    protected void configurarSidebar(String nomeUsuario, String cargo) {
        JPanel painelPerfil = new JPanel();
        painelPerfil.setBounds(0, 40, 220, 150);
        painelPerfil.setBackground(Cores.ROXO_SIDEBAR);
        painelPerfil.setLayout(null);

        char inicial = (nomeUsuario != null && !nomeUsuario.isEmpty()) ? nomeUsuario.charAt(0) : '?';
        
        JLabel lblAvatar = new JLabel("<html><div style='background-color:rgba(255,255,255,0.2); width:60px; height:60px; border-radius:50%; text-align:center; vertical-align:middle; line-height:60px; color:white; font-size:24px; font-weight:bold;'>" + inicial + "</div></html>");
        lblAvatar.setBounds(75, 0, 70, 70);
        painelPerfil.add(lblAvatar);

        JLabel lblNome = new JLabel(nomeUsuario);
        lblNome.setForeground(Color.WHITE);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setBounds(10, 80, 200, 20);
        painelPerfil.add(lblNome);

        JLabel lblCargo = new JLabel(cargo.toUpperCase());
        lblCargo.setForeground(new Color(200, 200, 255));
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCargo.setHorizontalAlignment(SwingConstants.CENTER);
        lblCargo.setBounds(10, 105, 200, 20);
        painelPerfil.add(lblCargo);

        painelSidebar.add(painelPerfil);
    }

    // Método privado que cria o menu falso para preencher espaço
    private void adicionarMenuLateral() {
        int yStart = 200; // Começa abaixo da área do perfil

        // Itens do menu (Visual apenas)
        criarItemMenu(" Início", yStart);
        criarItemMenu(" Editais", yStart + 50);
        criarItemMenu(" Inscrições", yStart + 100);
        criarItemMenu(" Relatórios", yStart + 150);
        criarItemMenu(" Ajuda", yStart + 200);

        // Linha separadora fina
        JSeparator sep = new JSeparator();
        sep.setBounds(20, yStart + 260, 180, 1);
        sep.setForeground(new Color(255, 255, 255, 40));
        sep.setBackground(new Color(255, 255, 255, 40));
        painelSidebar.add(sep);

        // Versão no rodapé
        JLabel lblVersao = new JLabel("Sismon v1.0");
        lblVersao.setForeground(new Color(255, 255, 255, 80));
        lblVersao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblVersao.setBounds(20, 600, 100, 20);
        painelSidebar.add(lblVersao);
    }

    // Cria um item de menu com efeito de brilho ao passar o mouse
    private void criarItemMenu(String texto, int y) {
        JLabel item = new JLabel("●  " + texto);
        item.setForeground(new Color(255, 255, 255, 150)); // Branco transparente
        item.setFont(new Font("Segoe UI", Font.BOLD, 14));
        item.setBounds(30, y, 190, 40);
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito Hover (Brilho)
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setForeground(Color.WHITE); // Fica branco puro
            }
            @Override
            public void mouseExited(MouseEvent e) {
                item.setForeground(new Color(255, 255, 255, 150)); // Volta ao transparente
            }
        });

        painelSidebar.add(item);
    }
    
    public void mostrarMensagem(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg);
    }
}