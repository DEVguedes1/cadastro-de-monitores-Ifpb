package view;

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.*; // Importante para o Layout do Painel de Usuário
import java.awt.event.ActionListener;

public class DashboardCoordenador extends JanelaModerna { 
    
    private JButton btnNovoEdital, btnListarEditais, btnListarAlunos, btnSair, btnBackup;

    public DashboardCoordenador(String nome) {
        super("Painel do Coordenador");
        inicializar(nome);
        configurarSidebar(nome, "Coordenador");
    }

    private void inicializar(String nome) {
        // --- 1. PAINEL DE USUÁRIO (SIDEBAR) ---
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Cores.ROXO_SIDEBAR);
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBounds(0, 0, 220, 180);

        JLabel lblProfileImage;
        try {
            // Tenta carregar a imagem
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/user_icon.png")); 
            // Verifica se carregou mesmo
            if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) throw new Exception("Erro img");
            
            // Redimensiona
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lblProfileImage = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            // FALLBACK: Cria uma "Bolinha" usando HTML/CSS simples
            // O fundo deve ser transparente (ou da cor da sidebar) para não criar o quadrado branco feio
            char inicial = (nome != null && !nome.isEmpty()) ? nome.charAt(0) : 'U';
            
            // Truque: background-color com alpha (transparencia) ou cor sólida igual sidebar
            lblProfileImage = new JLabel("<html><div style='background-color:#5a4dad; width:60px; height:60px; border-radius:50%; text-align:center; vertical-align:middle; line-height:60px; color:white; font-size:24px; font-family:Segoe UI;'>" + inicial + "</div></html>");
        }

        JLabel lblWelcome = new JLabel("Olá, " + nome);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel lblCargo = new JLabel("Coordenador");
        lblCargo.setForeground(new Color(200, 200, 255)); // Lilás claro
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Layout do Painel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 10, 0); // Margem topo
        userPanel.add(lblProfileImage, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 2, 0);
        userPanel.add(lblWelcome, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0); // Margem baixo
        userPanel.add(lblCargo, gbc);

        painelSidebar.add(userPanel);

        // --- 2. BOTÃO SAIR ---
        btnSair = Componentes.criarBotaoSidebar("Sair do Sistema");
        btnSair.setBackground(new java.awt.Color(90, 80, 200)); 
        btnSair.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnSair);

        // --- 3. CONTEÚDO (CARDS) ---
        JLabel lblTitulo = new JLabel("Visão Geral");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(40, 30, 300, 30);
        painelConteudo.add(lblTitulo);

        // Card 1: Novo Edital (Verde)
        btnNovoEdital = Componentes.criarBotaoCard("NOVO EDITAL", "+", Cores.VERDE);
        btnNovoEdital.setBounds(40, 90, 220, 120);
        painelConteudo.add(btnNovoEdital);

        // Card 2: Gerenciar Editais (Azul)
        btnListarEditais = Componentes.criarBotaoCard("EDITAIS ATIVOS", "LISTAR", Cores.AZUL);
        btnListarEditais.setBounds(280, 90, 220, 120);
        painelConteudo.add(btnListarEditais);

        // Card 3: Alunos (Laranja)
        btnListarAlunos = Componentes.criarBotaoCard("ALUNOS", "GERIR", Cores.LARANJA);
        btnListarAlunos.setBounds(520, 90, 220, 120);
        painelConteudo.add(btnListarAlunos);

        // Card 4: Backup (Roxo Escuro)
        btnBackup = Componentes.criarBotaoCard("BACKUP", "SALVAR", Cores.ROXO_ESCURO);
        btnBackup.setBounds(40, 230, 220, 120);
        painelConteudo.add(btnBackup);
        
        // Área Informativa / Fake Table
        JPanel painelTabelaFake = new JPanel();
        painelTabelaFake.setBackground(Cores.BRANCO);
        painelTabelaFake.setBounds(280, 230, 460, 120); // Ajustei para caber ao lado do backup
        painelTabelaFake.setBorder(BorderFactory.createLineBorder(new java.awt.Color(230,230,230)));
        painelTabelaFake.setLayout(null);
        
        JLabel lblAviso = new JLabel("<html><b>Dica:</b> Utilize o botão de Backup regularmente para<br>garantir a segurança dos dados do sistema.</html>");
        lblAviso.setForeground(Color.GRAY);
        lblAviso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAviso.setBounds(20, 20, 400, 80);
        painelTabelaFake.add(lblAviso);
        
        painelConteudo.add(painelTabelaFake);
    }

    // Listeners
    public void addNovoEditalListener(ActionListener l) { btnNovoEdital.addActionListener(l); }
    public void addListarEditaisListener(ActionListener l) { btnListarEditais.addActionListener(l); }
    public void addListarAlunosListener(ActionListener l) { btnListarAlunos.addActionListener(l); }
    public void addSairListener(ActionListener l) { btnSair.addActionListener(l); }
    public void addBackupListener(ActionListener l) { btnBackup.addActionListener(l); }
}