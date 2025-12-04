package view;

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.*; // Importante para GridBagLayout e Insets
import java.awt.event.ActionListener;

public class DashboardAluno extends JanelaModerna {
    
    private JButton btnVerEditais, btnMinhasInscricoes, btnMeusDados, btnSair;

    public DashboardAluno(String nome) {
        super("Área do Aluno");
        inicializar(nome);
    }

    private void inicializar(String nome) {
        // --- 1. PAINEL DE USUÁRIO (ÍCONE + NOME) NA SIDEBAR ---
        
    	// ... dentro de inicializar(String nome) ...

        // 1. PAINEL DE USUÁRIO
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Cores.ROXO_SIDEBAR);
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBounds(0, 0, 220, 180);

        JLabel lblProfileImage;
        try {
            // PROCURA EM src/images/user_icon.png
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/user.png")); 
            
            // Redimensiona a imagem para caber (Opcional, mas recomendado)
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblProfileImage = new JLabel(new ImageIcon(img));
            
        } catch (Exception e) {
            // SE NÃO ACHAR A FOTO, FAZ A BOLINHA COM A INICIAL
            System.out.println("Imagem não encontrada, usando fallback.");
            char inicial = (nome != null && !nome.isEmpty()) ? nome.charAt(0) : '?';
            lblProfileImage = new JLabel("<html><div style='background-color:white; width:70px; height:70px; border-radius:50%; text-align:center; vertical-align:middle; color:#6e48aa; font-size:30px; line-height:70px;'>" + inicial + "</div></html>");
        }

        // ... continua o código ...

        JLabel lblWelcome = new JLabel("Olá, " + nome);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Layout do Painel de Usuário
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 10, 0); // Margem topo
        userPanel.add(lblProfileImage, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0); // Margem baixo
        userPanel.add(lblWelcome, gbc);

        // Adiciona esse painel na Sidebar
        painelSidebar.add(userPanel);


        // --- 2. BOTÃO SAIR (Rodapé da Sidebar) ---
        btnSair = Componentes.criarBotaoSidebar("Sair");
        btnSair.setBackground(new Color(90, 80, 200));
        btnSair.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnSair);


        // --- 3. CONTEÚDO PRINCIPAL (CARDS) ---
        JLabel lblTitulo = new JLabel("Painel do Estudante");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(40, 30, 300, 30);
        painelConteudo.add(lblTitulo);

        // Card 1: Ver Editais (Verde)
        btnVerEditais = Componentes.criarBotaoCard("EDITAIS ABERTOS", "INSCREVER", Cores.VERDE);
        btnVerEditais.setBounds(40, 90, 220, 120);
        painelConteudo.add(btnVerEditais);

        // Card 2: Minhas Inscrições (Azul)
        btnMinhasInscricoes = Componentes.criarBotaoCard("MINHAS VAGAS", "ACOMPANHAR", Cores.AZUL);
        btnMinhasInscricoes.setBounds(280, 90, 220, 120);
        painelConteudo.add(btnMinhasInscricoes);

        // Card 3: Meus Dados (Laranja)
        btnMeusDados = Componentes.criarBotaoCard("MEU PERFIL", "EDITAR", Cores.LARANJA);
        btnMeusDados.setBounds(520, 90, 220, 120);
        painelConteudo.add(btnMeusDados);
        
        // Área Informativa
        JPanel painelInfo = new JPanel();
        painelInfo.setBackground(Color.WHITE);
        painelInfo.setBounds(40, 240, 700, 250);
        painelInfo.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));
        painelInfo.setLayout(null);
        
        JLabel lblDica = new JLabel("<html><b>Dica:</b> Acompanhe sempre o status das suas inscrições.<br>O resultado sai aqui mesmo no sistema assim que o coordenador liberar.</html>");
        lblDica.setForeground(Color.GRAY);
        lblDica.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblDica.setBounds(30, 30, 600, 60);
        painelInfo.add(lblDica);
        
        painelConteudo.add(painelInfo);
    }

    public void addVerEditaisListener(ActionListener l) { btnVerEditais.addActionListener(l); }
    public void addMinhasInscricoesListener(ActionListener l) { btnMinhasInscricoes.addActionListener(l); }
    public void addMeusDadosListener(ActionListener l) { btnMeusDados.addActionListener(l); }
    public void addSairListener(ActionListener l) { btnSair.addActionListener(l); }
}