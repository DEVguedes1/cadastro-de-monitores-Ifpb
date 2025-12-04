package view;

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

public class CadastroAlunoView extends JFrame {

    private JTextField txtNome, txtMatricula, txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar, btnVoltar;

    public CadastroAlunoView() {
        setTitle("Novo Aluno");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        inicializar();
    }

    private void inicializar() {
        // --- PAINEL ESQUERDO (VERDE) ---
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(Cores.VERDE); // Verde para Alunos
        painelEsquerdo.setBounds(0, 0, 350, 600);
        painelEsquerdo.setLayout(null);
        add(painelEsquerdo);

        JLabel lblLogo = new JLabel("SOU ALUNO");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(40, 200, 300, 40);
        painelEsquerdo.add(lblLogo);

        JLabel lblDesc = new JLabel("<html>Crie sua conta para acessar<br>os editais de monitoria.</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblDesc.setForeground(new Color(235, 255, 235));
        lblDesc.setBounds(40, 250, 300, 60);
        painelEsquerdo.add(lblDesc);

        // --- PAINEL DIREITO ---
        JPanel painelDireito = new JPanel();
        painelDireito.setBackground(Color.WHITE);
        painelDireito.setBounds(350, 0, 550, 600);
        painelDireito.setLayout(null);
        add(painelDireito);

        JLabel lblTitulo = new JLabel("Cadastro de Estudante");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(50, 30, 400, 40);
        painelDireito.add(lblTitulo);

        // Campos
        addLabel(painelDireito, "Nome Completo", 90);
        txtNome = addCampo(painelDireito, 115);

        addLabel(painelDireito, "Matrícula", 170);
        txtMatricula = addCampo(painelDireito, 195);

        addLabel(painelDireito, "E-mail", 250);
        txtEmail = addCampo(painelDireito, 275);

        addLabel(painelDireito, "Crie uma Senha", 330);
        txtSenha = new JPasswordField();
        txtSenha.setBounds(50, 355, 400, 40);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,10,5,10)));
        painelDireito.add(txtSenha);

        // Botões
        btnCadastrar = new JButton("FINALIZAR CADASTRO");
        btnCadastrar.setBounds(50, 420, 400, 50);
        btnCadastrar.setBackground(Cores.VERDE); // Botão Verde
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(Componentes.FONT_BOTAO);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);
        painelDireito.add(btnCadastrar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(50, 480, 400, 40);
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(Color.GRAY);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVoltar.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        btnVoltar.setFocusPainted(false);
        painelDireito.add(btnVoltar);
    }

    private void addLabel(JPanel p, String t, int y) {
        JLabel l = new JLabel(t);
        l.setFont(Componentes.FONT_SUB);
        l.setForeground(Color.GRAY);
        l.setBounds(50, y, 300, 20);
        p.add(l);
    }
    private JTextField addCampo(JPanel p, int y) {
        JTextField t = new JTextField();
        t.setBounds(50, y, 400, 40);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,10,5,10)));
        p.add(t);
        return t;
    }

    public String getNome() { return txtNome.getText(); }
    public String getMatricula() { return txtMatricula.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public void addSalvarListener(ActionListener l) { btnCadastrar.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}