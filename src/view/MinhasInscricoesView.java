package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.style.Componentes;
import view.style.Cores;
import view.style.UIUtils;

public class MinhasInscricoesView extends JanelaModerna {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnVoltar, btnDesistir;

    public MinhasInscricoesView() {
        super("Minhas Inscrições");
        inicializar();
    }

    private void inicializar() {
        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Voltar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // Cabeçalho
        JLabel lblTitulo = new JLabel("Minhas Monitorias");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 30, 400, 30);
        painelConteudo.add(lblTitulo);

        // Tabela
        String[] colunas = {"ID_EDITAL", "Edital", "Disciplina", "Nota Final", "Situação"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modelo);
        Componentes.estilizarTabela(tabela);
        UIUtils.styleTable(tabela);
        
        // Esconde ID
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
        
        // Cores
        tabela.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer(4));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 90, 720, 380);
        painelConteudo.add(scroll);

        // Botão Desistir
        btnDesistir = new JButton("DESISTIR DA VAGA");
        btnDesistir.setBounds(30, 500, 200, 45);
        btnDesistir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDesistir.setFocusPainted(false);
        UIUtils.styleButton(btnDesistir, Cores.VERMELHO);
        painelConteudo.add(btnDesistir);
    }

    public DefaultTableModel getModelo() { return modelo; }
    public Long getIdEditalSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return (Long) tabela.getValueAt(linha, 0);
        return null;
    }
    public String getDisciplinaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return tabela.getValueAt(linha, 2).toString();
        return null;
    }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void addDesistirListener(ActionListener l) { btnDesistir.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}