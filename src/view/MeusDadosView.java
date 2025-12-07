package view;

/**
 * Tela de Auto-gestão de Perfil (Aluno).
 * <p>
 * Permite ao estudante visualizar e atualizar suas informações cadastrais.
 * <p>
 * <b>Regras de Interface:</b>
 * <ul>
 * <li>O campo <b>Matrícula</b> é exibido como somente leitura (não editável).</li>
 * <li>Campos de Nome, E-mail e Senha são editáveis.</li>
 * </ul>
 */

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import models.Aluno;

public class MeusDadosView extends JanelaModerna {

    private JTextField txtNome, txtEmail, txtSenha;
    private JTextField txtMatricula; 
    private JButton btnSalvar, btnVoltar;

    public MeusDadosView(Aluno aluno) {
        super("Meu Perfil");
        inicializar(aluno);
    }

    private void inicializar(Aluno aluno) {
        // Sidebar
        btnVoltar = Componentes.criarBotaoSidebar("Voltar");
        btnVoltar.setBounds(0, 550, 220, 50);
        painelSidebar.add(btnVoltar);

        // --- CONTEÚDO ---
        JLabel lblTitulo = new JLabel("Meus Dados Cadastrais");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(40, 30, 400, 30);
        painelConteudo.add(lblTitulo);

        // Card Branco do Formulário
        JPanel painelForm = new JPanel();
        painelForm.setBackground(Color.WHITE);
        painelForm.setBounds(40, 80, 700, 400);
        painelForm.setLayout(null);
        painelForm.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        painelConteudo.add(painelForm);

        // Campos
        addLabel(painelForm, "Matrícula (Não alterável)", 30, 30);
        txtMatricula = addCampo(painelForm, 30, 55, aluno.getMatricula());
        txtMatricula.setEditable(false);
        txtMatricula.setBackground(new Color(245, 245, 245)); // Cinza claro
        txtMatricula.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        addLabel(painelForm, "Nome Completo", 30, 110);
        txtNome = addCampo(painelForm, 30, 135, aluno.getNomeDoAluno());

        addLabel(painelForm, "E-mail de Contato", 30, 190);
        txtEmail = addCampo(painelForm, 30, 215, aluno.getEmail());

        addLabel(painelForm, "Senha de Acesso", 30, 270);
        txtSenha = addCampo(painelForm, 30, 295, aluno.getSenha());

        // Botão Salvar (Laranja = Edição)
        btnSalvar = new JButton("SALVAR ALTERAÇÕES");
        btnSalvar.setBounds(30, 350, 640, 45); // Largura total do form
        btnSalvar.setBackground(Cores.LARANJA);
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

    private JTextField addCampo(JPanel p, int x, int y, String valor) {
        JTextField t = new JTextField(valor);
        t.setBounds(x, y, 640, 40); // Campo largo
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        p.add(t);
        return t;
    }

    // Getters
    public String getNome() { return txtNome.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return txtSenha.getText(); }

    public void addSalvarListener(ActionListener l) { btnSalvar.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}