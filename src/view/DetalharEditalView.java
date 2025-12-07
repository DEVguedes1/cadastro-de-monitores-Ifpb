package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.style.Cores;
import view.style.Componentes;
import view.style.UIUtils; // <--- Importante
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

public class DetalharEditalView extends JanelaModerna {

    private JLabel lblTitulo, lblPeriodo;
    private JTextField txtNomeDisc, txtVagas, txtPesoNota, txtPesoCRE;
    private JButton btnAdicionar, btnVoltar, btnEditarDisc, btnExcluirDisc;
    private JTable tabelaDisciplinas;
    private DefaultTableModel modeloTabela;

    public DetalharEditalView() {
        super("Configurar Edital");
        inicializar();
    }

    private void inicializar() {
        // ... (Sidebar e Cabeçalho iguais) ...
        btnVoltar = Componentes.criarBotaoSidebar("Voltar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        lblTitulo = new JLabel("Edital: Carregando...");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(30, 20, 400, 30);
        painelConteudo.add(lblTitulo);

        lblPeriodo = new JLabel("Período: --/--/---- até --/--/----");
        lblPeriodo.setFont(Componentes.FONT_SUB);
        lblPeriodo.setForeground(Color.GRAY);
        lblPeriodo.setBounds(30, 55, 400, 20);
        painelConteudo.add(lblPeriodo);

        // --- FORMULÁRIO ---
        JPanel painelForm = new JPanel();
        painelForm.setBackground(Color.WHITE);
        painelForm.setBounds(30, 90, 720, 100);
        painelForm.setLayout(null);
        painelForm.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        painelConteudo.add(painelForm);

        addLabelForm(painelForm, "Disciplina", 10, 10);
        txtNomeDisc = addCampoForm(painelForm, 10, 35, 200);

        addLabelForm(painelForm, "Vagas", 220, 10);
        txtVagas = addCampoForm(painelForm, 220, 35, 70);

        addLabelForm(painelForm, "Peso Nota", 300, 10);
        txtPesoNota = addCampoForm(painelForm, 300, 35, 80);

        addLabelForm(painelForm, "Peso CRE", 390, 10);
        txtPesoCRE = addCampoForm(painelForm, 390, 35, 80);

        // BOTÃO ADICIONAR (Verde)
        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setBounds(500, 35, 200, 35);
        UIUtils.styleButton(btnAdicionar, Cores.VERDE); // <--- AQUI
        painelForm.add(btnAdicionar);

        // --- TABELA ---
        String[] colunas = {"Nome", "Vagas", "Peso Nota", "Peso CRE"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tabelaDisciplinas = new JTable(modeloTabela);
        UIUtils.styleTable(tabelaDisciplinas); // <--- AQUI
        
        JScrollPane scroll = new JScrollPane(tabelaDisciplinas);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(30, 210, 720, 300);
        painelConteudo.add(scroll);

        // --- RODAPÉ (Botões de Baixo) ---
        btnEditarDisc = new JButton("Editar Selecionada");
        btnEditarDisc.setBounds(30, 530, 180, 40);
        UIUtils.styleButton(btnEditarDisc, Cores.LARANJA); // <--- AQUI
        painelConteudo.add(btnEditarDisc);

        btnExcluirDisc = new JButton("Remover");
        btnExcluirDisc.setBounds(220, 530, 150, 40);
        UIUtils.styleButton(btnExcluirDisc, Cores.VERMELHO); // <--- AQUI
        painelConteudo.add(btnExcluirDisc);
    }

    // ... (Métodos auxiliares addLabelForm, addCampoForm e Getters mantidos) ...
    private void addLabelForm(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(Color.GRAY);
        l.setBounds(x, y, 100, 20);
        p.add(l);
    }
    private JTextField addCampoForm(JPanel p, int x, int y, int w) {
        JTextField t = new JTextField();
        t.setBounds(x, y, w, 35);
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        p.add(t);
        return t;
    }
    public void setTextoTitulo(String t) { lblTitulo.setText(t); }
    public void setTextoPeriodo(String t) { lblPeriodo.setText(t); }
    public String getNomeDisc() { return txtNomeDisc.getText(); }
    public String getVagas() { return txtVagas.getText(); }
    public String getPesoNota() { return txtPesoNota.getText(); }
    public String getPesoCRE() { return txtPesoCRE.getText(); }
    public DefaultTableModel getModeloTabela() { return modeloTabela; }
    public String getDisciplinaSelecionada() {
        int linha = tabelaDisciplinas.getSelectedRow();
        if (linha >= 0) return tabelaDisciplinas.getValueAt(linha, 0).toString();
        return null;
    }
    public void limparCampos() { txtNomeDisc.setText(""); txtVagas.setText(""); txtPesoNota.setText(""); txtPesoCRE.setText(""); }
    public void addAdicionarListener(ActionListener l) { btnAdicionar.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void addEditarDiscListener(ActionListener l) { btnEditarDisc.addActionListener(l); }
    public void addExcluirDiscListener(ActionListener l) { btnExcluirDisc.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}