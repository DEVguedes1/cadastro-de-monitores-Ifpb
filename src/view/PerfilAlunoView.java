package view;

/**
 * Ficha Detalhada do Aluno (Visão Administrativa).
 * <p>
 * Exibe um resumo completo da vida acadêmica do estudante no contexto da monitoria.
 * Composta por:
 * <ul>
 * <li><b>Cartão de Dados:</b> Informações pessoais e CRE.</li>
 * <li><b>Histórico:</b> Tabela com todas as participações em editais passados.</li>
 * </ul>
 * Permite ao coordenador acessar a edição dos dados deste aluno.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.Aluno;
import view.style.Componentes;
import view.style.Cores;
import view.style.UIUtils;

public class PerfilAlunoView extends JanelaModerna {

    private JTable tabelaHistorico;
    private DefaultTableModel modeloHistorico;
    private JButton btnVoltar, btnEditar;

    public PerfilAlunoView(Aluno aluno) {
        super("Perfil do Aluno");
        inicializar(aluno);
    }

    private void inicializar(Aluno aluno) {
        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Voltar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // --- CONTEÚDO ---
        JLabel lblTitulo = new JLabel("Ficha Acadêmica");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 20, 300, 30);
        painelConteudo.add(lblTitulo);

        // Bloco de Informações (Card Branco com Sombra simulada)
        JPanel painelInfo = new JPanel();
        painelInfo.setBackground(Color.WHITE);
        painelInfo.setBounds(30, 70, 720, 150);
        painelInfo.setLayout(null);
        painelInfo.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));
        painelConteudo.add(painelInfo);

        addInfo(painelInfo, "Nome Completo", aluno.getNomeDoAluno(), 20, 20);
        addInfo(painelInfo, "Matrícula", aluno.getMatricula(), 400, 20);
        addInfo(painelInfo, "E-mail de Contato", aluno.getEmail(), 20, 80);
        addInfo(painelInfo, "CRE Atual", String.valueOf(aluno.getCre()), 400, 80);

        // Tabela Histórico
        JLabel lblHist = new JLabel("Histórico de Monitorias");
        lblHist.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHist.setForeground(Cores.TEXTO_ESCURO);
        lblHist.setBounds(30, 240, 300, 20);
        painelConteudo.add(lblHist);

        String[] colunas = {"Edital", "Disciplina", "Nota Final", "Situação"};
        modeloHistorico = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaHistorico = new JTable(modeloHistorico);
        Componentes.estilizarTabela(tabelaHistorico);
        tabelaHistorico.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer(3));
        UIUtils.styleTable(tabelaHistorico);
        
        JScrollPane scroll = new JScrollPane(tabelaHistorico);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 270, 720, 250);
        painelConteudo.add(scroll);

        // Botão Editar
        btnEditar = new JButton("EDITAR DADOS CADASTRAIS");
        btnEditar.setBounds(30, 540, 250, 45);
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEditar.setFocusPainted(false);
        UIUtils.styleButton(btnEditar, Cores.LARANJA);
        painelConteudo.add(btnEditar);
    }

    private void addInfo(JPanel p, String titulo, String valor, int x, int y) {
        JLabel lblT = new JLabel(titulo.toUpperCase());
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblT.setForeground(Color.GRAY);
        lblT.setBounds(x, y, 200, 20);
        p.add(lblT);

        JLabel lblV = new JLabel(valor);
        lblV.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblV.setForeground(Cores.TEXTO_ESCURO);
        lblV.setBounds(x, y + 20, 350, 25);
        p.add(lblV);
    }

    public DefaultTableModel getModeloHistorico() { return modeloHistorico; }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void addEditarListener(ActionListener l) { btnEditar.addActionListener(l); }
}