package view;

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import models.recurses.Disciplina;

public class EditarDisciplinaDialog extends JDialog {

    private JTextField txtNome, txtVagas, txtPesoNota, txtPesoCRE;
    private JButton btnSalvar;

    public EditarDisciplinaDialog(JFrame parent, Disciplina disciplina) {
        super(parent, "Editar Disciplina", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Editar Disciplina");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 400, 30);
        add(lblTitulo);

        addLabel("Nome da Disciplina", 70);
        txtNome = addCampo(95);
        txtNome.setText(disciplina.getNomeDisciplina());

        addLabel("Vagas", 150);
        txtVagas = addCampo(175);
        txtVagas.setText(String.valueOf(disciplina.getQntdVagas()));

        addLabel("Peso Nota (0.0 a 1.0)", 230);
        txtPesoNota = addCampo(255);
        txtPesoNota.setText(String.valueOf(disciplina.getPesoNota()));

        addLabel("Peso CRE (Restante)", 310);
        txtPesoCRE = addCampo(335);
        txtPesoCRE.setText(String.valueOf(disciplina.getPesoCRE()));

        btnSalvar = new JButton("SALVAR");
        btnSalvar.setBounds(50, 400, 300, 45);
        btnSalvar.setBackground(Cores.LARANJA); // Laranja pois é edição
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(Componentes.FONT_BOTAO);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
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
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        add(t);
        return t;
    }

    public String getNome() { return txtNome.getText(); }
    public String getVagas() { return txtVagas.getText(); }
    public String getPesoNota() { return txtPesoNota.getText(); }
    public String getPesoCRE() { return txtPesoCRE.getText(); }
    public void addSalvarListener(ActionListener l) { btnSalvar.addActionListener(l); }
}