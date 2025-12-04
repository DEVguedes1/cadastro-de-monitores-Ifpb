package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.recurses.Edital;
import view.style.Componentes;
import view.style.Cores;
import view.style.UIUtils; // Certifique-se que importou sua classe de utilitários

public class AlunoInscricaoView extends JanelaModerna {

    private JTable tabela;
    private JButton btnInscrever, btnVoltar;
    private DefaultTableModel modelo;

    public AlunoInscricaoView(Edital edital) {
        super("Fazer Inscrição");
        inicializar(edital);
    }

    private void inicializar(Edital edital) {
        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Voltar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // Cabeçalho
        JLabel lblTitulo = new JLabel("Edital: " + edital.getNumEdital());
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 20, 400, 30);
        painelConteudo.add(lblTitulo);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel lblData = new JLabel("Período: " + edital.getDataIncio().format(fmt) + " até " + edital.getDataFinal().format(fmt));
        lblData.setFont(Componentes.FONT_SUB);
        lblData.setForeground(Color.GRAY);
        lblData.setBounds(30, 55, 400, 20);
        painelConteudo.add(lblData);

        JLabel lblInstr = new JLabel("Selecione a disciplina na tabela abaixo:");
        lblInstr.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblInstr.setForeground(Cores.AZUL);
        lblInstr.setBounds(30, 90, 400, 20);
        painelConteudo.add(lblInstr);

        // --- CORREÇÃO DA TABELA E SCROLL ---
        
        // 1. Cria o modelo e a tabela
        modelo = TabelaSemResultadoBuilder.montarTabela(edital);
        tabela = new JTable(modelo);
        
        // 2. Aplica o estilo visual
        UIUtils.styleTable(tabela); 
        
        // 3. Cria o ScrollPane JÁ com a tabela dentro (Isso substitui o setViewportView)
        JScrollPane scroll = new JScrollPane(tabela);
        
        // 4. Configura o visual do Scroll
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 120, 720, 350);
        
        // 5. Adiciona na tela
        painelConteudo.add(scroll);

        // --- FIM DA CORREÇÃO ---

        // Botão Inscrever
        btnInscrever = new JButton("REALIZAR INSCRIÇÃO NA DISCIPLINA");
        btnInscrever.setBounds(30, 500, 350, 45);
        btnInscrever.setBackground(Cores.VERDE);
        btnInscrever.setForeground(Color.WHITE);
        btnInscrever.setFont(Componentes.FONT_BOTAO);
        btnInscrever.setFocusPainted(false);
        btnInscrever.setBorderPainted(false);
        painelConteudo.add(btnInscrever);
    }

    public String getDisciplinaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return tabela.getValueAt(linha, 0).toString();
        return null;
    }
    public void addInscreverListener(ActionListener l) { btnInscrever.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}