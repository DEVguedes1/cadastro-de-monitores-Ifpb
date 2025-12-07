package view;

/**
 * Formulário de Criação de Edital.
 * <p>
 * Coleta os dados iniciais (Número, Datas, Limite de Inscrições) para
 * instanciar um novo processo seletivo no sistema.
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class CadastrarEditalView extends JanelaModerna {

    private JTextField txtNumero, txtMaxInscricoes;
    private JFormattedTextField txtDataInicio, txtDataFim;
    private JButton btnSalvar, btnVoltar;

    public CadastrarEditalView() {
        super("Novo Edital");
        inicializar();
    }

    private void inicializar() {
        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Cancelar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // --- CONTEÚDO ---
        JLabel lblTitulo = new JLabel("Criar Novo Edital");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(40, 30, 400, 30);
        painelConteudo.add(lblTitulo);

        // Card Branco
        JPanel painelForm = new JPanel();
        painelForm.setBackground(Color.WHITE);
        painelForm.setBounds(40, 80, 700, 400);
        painelForm.setLayout(null);
        painelForm.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        painelConteudo.add(painelForm);

        addLabel(painelForm, "Número do Edital (Ex: 2025.1)", 30, 30);
        txtNumero = addCampo(painelForm, 30, 55);

        addLabel(painelForm, "Máximo de Inscrições por Aluno", 380, 30);
        txtMaxInscricoes = addCampo(painelForm, 380, 55);
        txtMaxInscricoes.setSize(290, 40); // Campo menor na largura

        addLabel(painelForm, "Data de Início", 30, 120);
        txtDataInicio = addCampoData(painelForm, 30, 145);

        addLabel(painelForm, "Data de Término", 380, 120);
        txtDataFim = addCampoData(painelForm, 380, 145);
        txtDataFim.setSize(290, 40);

        // Botão Salvar
        btnSalvar = new JButton("CRIAR E CONFIGURAR DISCIPLINAS");
        btnSalvar.setBounds(30, 250, 640, 50);
        btnSalvar.setBackground(Cores.VERDE);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(Componentes.FONT_BOTAO);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        painelForm.add(btnSalvar);
    }

    private void addLabel(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        l.setBounds(x, y, 300, 20);
        p.add(l);
    }
    private JTextField addCampo(JPanel p, int x, int y) {
        JTextField t = new JTextField();
        t.setBounds(x, y, 320, 40);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        p.add(t);
        return t;
    }
    private JFormattedTextField addCampoData(JPanel p, int x, int y) {
        JFormattedTextField t;
        try {
            MaskFormatter m = new MaskFormatter("##/##/####");
            m.setPlaceholderCharacter('_');
            t = new JFormattedTextField(m);
        } catch (ParseException e) { t = new JFormattedTextField(); }
        t.setBounds(x, y, 320, 40);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        p.add(t);
        return t;
    }

    public String getNumero() { return txtNumero.getText(); }
    public String getDataInicio() { return txtDataInicio.getText(); }
    public String getDataFim() { return txtDataFim.getText(); }
    public String getMaxInscricoes() { return txtMaxInscricoes.getText(); }
    public void addSalvarListener(ActionListener l) { btnSalvar.addActionListener(l); }
    public void addCancelarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}