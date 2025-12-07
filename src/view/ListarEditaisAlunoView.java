package view;

/**
 * Catálogo de Editais (Visão do Estudante).
 * <p>
 * Apresenta ao aluno as oportunidades de monitoria disponíveis.
 * <p>
 * <b>Funcionalidades:</b>
 * <ul>
 * <li>Lista apenas editais com status "Aberto" ou "Não Iniciado".</li>
 * <li>Permite iniciar o processo de inscrição em disciplinas.</li>
 * </ul>
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.event.ActionListener;

public class ListarEditaisAlunoView extends JanelaModerna {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton btnInscrever, btnVoltar;

    public ListarEditaisAlunoView() {
        super("Editais Disponíveis");
        inicializar();
    }

    private void inicializar() {
        JLabel lblTitulo = new JLabel("Oportunidades Abertas");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 30, 400, 30);
        painelConteudo.add(lblTitulo);

        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Voltar ao Painel");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // Tabela
        String[] colunas = {"ID", "Número", "Início", "Fim", "Status"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabela = new JTable(modelo);
        Componentes.estilizarTabela(tabela); // Estilo

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 80, 720, 400);
        painelConteudo.add(scroll);

        // Botão Inscrever
        btnInscrever = new JButton("VER DISCIPLINAS / INSCREVER-SE");
        btnInscrever.setBounds(30, 500, 300, 45);
        btnInscrever.setBackground(Cores.VERDE);
        btnInscrever.setForeground(Color.WHITE);
        btnInscrever.setFont(Componentes.FONT_BOTAO);
        btnInscrever.setFocusPainted(false);
        btnInscrever.setBorderPainted(false);
        painelConteudo.add(btnInscrever);
    }

    public DefaultTableModel getModelo() { return modelo; }
    public String getIdSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) return tabela.getValueAt(linha, 0).toString();
        return null;
    }
    public void addInscreverListener(ActionListener l) { btnInscrever.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}