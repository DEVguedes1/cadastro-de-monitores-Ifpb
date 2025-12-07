package view;

/**
 * Modal de Edição de Aluno.
 * <p>
 * Janela flutuante para alteração rápida de dados cadastrais (Nome, E-mail, CRE).
 */

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import models.Aluno;

public class EditarAlunoDialog extends JDialog {

    private JTextField txtNome, txtEmail, txtCRE, txtMatricula;
    private JButton btnSalvar;

    public EditarAlunoDialog(JFrame parent, Aluno aluno) {
        super(parent, "Editar Aluno", true); // Modal
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE); // Fundo Branco Limpo

        // Título
        JLabel lblTitulo = new JLabel("Editar Cadastro");
        lblTitulo.setFont(Componentes.FONT_TITULO); // Usa a fonte do estilo
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 400, 30);
        add(lblTitulo);

        // Campo Matrícula (Bloqueado)
        addLabel("Matrícula (Fixo)", 70);
        txtMatricula = addCampo(95);
        txtMatricula.setText(aluno.getMatricula());
        txtMatricula.setEditable(false);
        txtMatricula.setBackground(new Color(245, 245, 245)); // Cinza claro para indicar readonly
        txtMatricula.setForeground(Color.GRAY);

        // Campos Editáveis
        addLabel("Nome Completo", 150);
        txtNome = addCampo(175);
        txtNome.setText(aluno.getNomeDoAluno());

        addLabel("E-mail", 230);
        txtEmail = addCampo(255);
        txtEmail.setText(aluno.getEmail());

        addLabel("CRE", 310);
        txtCRE = addCampo(335);
        txtCRE.setText(String.valueOf(aluno.getCre()));

        // Botão Salvar
        btnSalvar = new JButton("SALVAR DADOS");
        btnSalvar.setBounds(50, 400, 300, 45);
        btnSalvar.setBackground(Cores.LARANJA); // Laranja para edição
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(Componentes.FONT_BOTAO);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnSalvar);
    }

    // Métodos Auxiliares de Estilo
    private void addLabel(String t, int y) {
        JLabel l = new JLabel(t);
        l.setBounds(50, y, 300, 20);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        add(l);
    }

    private JTextField addCampo(int y) {
        JTextField t = new JTextField();
        t.setBounds(50, y, 300, 35); // Altura boa para clicar
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Borda suave com padding interno
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(t);
        return t;
    }

    // Getters
    public String getNome() { return txtNome.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getCRE() { return txtCRE.getText(); }
    
    // Listener
    public void addSalvarListener(ActionListener l) { btnSalvar.addActionListener(l); }
}