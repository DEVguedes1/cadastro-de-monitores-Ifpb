package view;

/**
 * Tela de Gerenciamento de Base de Alunos.
 * <p>
 * Visão utilizada pelo Coordenador para:
 * <ul>
 * <li>Visualizar todos os alunos cadastrados no sistema.</li>
 * <li>Pesquisar alunos por nome em tempo real.</li>
 * <li>Acessar o perfil detalhado de um estudante específico.</li>
 * </ul>
 * Utiliza uma {@link JTable} customizada para exibição dos dados.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ListarAlunosView extends JanelaModerna {

    private JTextField txtBusca;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnVoltar, btnVerPerfil;

    public ListarAlunosView() {
        super("Gerenciar Alunos");
        inicializar();
    }

    private void inicializar() {
        JLabel lblTitulo = new JLabel("Alunos Cadastrados");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 30, 400, 30);
        painelConteudo.add(lblTitulo);

        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Voltar ao Painel");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // Campo de Busca Moderno
        JLabel lblBusca = new JLabel("Buscar por Nome:");
        lblBusca.setFont(Componentes.FONT_SUB);
        lblBusca.setBounds(30, 70, 150, 20);
        painelConteudo.add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(30, 95, 720, 40); // Mais alto
        txtBusca.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        painelConteudo.add(txtBusca);

        // Tabela
        String[] colunas = {"Matrícula", "Nome", "E-mail", "CRE"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabela = new JTable(modelo);
        Componentes.estilizarTabela(tabela); // Estilo moderno

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 150, 720, 330);
        painelConteudo.add(scroll);

        // Botão Ver Perfil
        btnVerPerfil = new JButton("ABRIR PERFIL / DETALHES");
        btnVerPerfil.setBounds(30, 500, 250, 45);
        btnVerPerfil.setBackground(Cores.AZUL);
        btnVerPerfil.setForeground(Color.WHITE);
        btnVerPerfil.setFont(Componentes.FONT_BOTAO);
        btnVerPerfil.setFocusPainted(false);
        btnVerPerfil.setBorderPainted(false);
        painelConteudo.add(btnVerPerfil);
    }

    public DefaultTableModel getModelo() { return modelo; }
    public String getTextoBusca() { return txtBusca.getText(); }
    public String getMatriculaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return tabela.getValueAt(linha, 0).toString();
        return null;
    }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void addVerPerfilListener(ActionListener l) { btnVerPerfil.addActionListener(l); }
    public void addBuscaListener(KeyListener k) { txtBusca.addKeyListener(k); }
}