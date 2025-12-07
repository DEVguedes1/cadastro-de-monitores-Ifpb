package view;

/**
 * Modal de Edição de Edital.
 * <p>
 * Janela flutuante para correção de datas e limites de inscrição.
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import models.recurses.Edital;

public class EditarEditalDialog extends JDialog {

    private JTextField txtNumero, txtMaxInscricoes;
    private JFormattedTextField txtDataInicio, txtDataFim;
    private JButton btnSalvar;

    public EditarEditalDialog(JFrame parent, Edital edital) {
        super(parent, "Configurar Edital", true);
        setSize(400, 500); // Um pouco mais alto que o de aluno
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Configurações do Edital");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 400, 30);
        add(lblTitulo);

        // ID/Número (Bloqueado)
        addLabel("Número/ID (Fixo)", 70);
        txtNumero = addCampo(95);
        txtNumero.setText(edital.getNumEdital());
        txtNumero.setEditable(false);
        txtNumero.setBackground(new Color(245, 245, 245));
        txtNumero.setForeground(Color.GRAY);

        // Formatador para preencher os campos de data
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Datas
        addLabel("Data Início", 150);
        txtDataInicio = addCampoData(175);
        txtDataInicio.setText(edital.getDataIncio().format(fmt));

        addLabel("Data Término", 230);
        txtDataFim = addCampoData(255);
        txtDataFim.setText(edital.getDataFinal().format(fmt));

        // Limite
        addLabel("Máx. Inscrições por Aluno", 310);
        txtMaxInscricoes = addCampo(335);
        txtMaxInscricoes.setText(String.valueOf(edital.getMaxInc()));

        // Botão Salvar
        btnSalvar = new JButton("SALVAR ALTERAÇÕES");
        btnSalvar.setBounds(50, 400, 300, 45);
        btnSalvar.setBackground(Cores.LARANJA);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(Componentes.FONT_BOTAO);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnSalvar);
    }

    private void addLabel(String t, int y) {
        JLabel l = new JLabel(t);
        l.setBounds(50, y, 300, 20);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        add(l);
    }

    private JTextField addCampo(int y) {
        JTextField t = new JTextField();
        t.setBounds(50, y, 300, 35);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(t);
        return t;
    }

    // Campo Especial para Data (Com Máscara)
    private JFormattedTextField addCampoData(int y) {
        JFormattedTextField t;
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            t = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            t = new JFormattedTextField();
        }
        t.setBounds(50, y, 300, 35);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(t);
        return t;
    }

    public String getDataInicio() { return txtDataInicio.getText(); }
    public String getDataFim() { return txtDataFim.getText(); }
    public String getMaxInscricoes() { return txtMaxInscricoes.getText(); }
    
    public void addSalvarListener(ActionListener l) { btnSalvar.addActionListener(l); }
}