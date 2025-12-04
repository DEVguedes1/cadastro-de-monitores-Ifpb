package view;

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame { // Herda JFrame direto para layout livre

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnCriarConta;

    public LoginView() {
        setTitle("Login");
        setSize(900, 600); // Tela cheia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Fundo dividido: Esquerda Roxa, Direita Branca (Estilo Dashboard moderno)
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(Cores.ROXO_SIDEBAR);
        painelEsquerdo.setBounds(0, 0, 400, 600);
        painelEsquerdo.setLayout(null);
        add(painelEsquerdo);
        
        // Texto no painel roxo
        JLabel lblLogo = new JLabel("SISTEMA DE");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(50, 200, 300, 40);
        painelEsquerdo.add(lblLogo);
        
        JLabel lblLogo2 = new JLabel("MONITORIA");
        lblLogo2.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblLogo2.setForeground(Color.WHITE);
        lblLogo2.setBounds(50, 240, 300, 40);
        painelEsquerdo.add(lblLogo2);

        // --- PAINEL DE LOGIN (DIREITA) ---
        JPanel painelDireito = new JPanel();
        painelDireito.setBackground(Color.WHITE);
        painelDireito.setBounds(400, 0, 500, 600);
        painelDireito.setLayout(null);
        add(painelDireito);

        JLabel lblBemVindo = new JLabel("Bem-vindo de volta");
        lblBemVindo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBemVindo.setForeground(Cores.TEXTO_ESCURO);
        lblBemVindo.setBounds(50, 100, 400, 40);
        painelDireito.add(lblBemVindo);

        // Email
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(Componentes.FONT_SUB);
        lblEmail.setBounds(50, 180, 100, 20);
        painelDireito.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(50, 205, 350, 40);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Borda suave
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        painelDireito.add(txtEmail);

        // Senha
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(Componentes.FONT_SUB);
        lblSenha.setBounds(50, 260, 100, 20);
        painelDireito.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(50, 285, 350, 40);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        painelDireito.add(txtSenha);

        // Botão Entrar
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBounds(50, 360, 350, 45);
        btnEntrar.setBackground(Cores.ROXO_SIDEBAR);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(Componentes.FONT_BOTAO);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        painelDireito.add(btnEntrar);

        // Botão Criar Conta
        btnCriarConta = new JButton("Criar nova conta");
        btnCriarConta.setBounds(50, 420, 350, 30);
        btnCriarConta.setBackground(Color.WHITE);
        btnCriarConta.setForeground(Cores.ROXO_SIDEBAR);
        btnCriarConta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCriarConta.setBorder(null);
        btnCriarConta.setFocusPainted(false);
        btnCriarConta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelDireito.add(btnCriarConta);
    }

    // Getters e Listeners (Mantidos iguais para não quebrar o Controller)
    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public void addLoginListener(ActionListener l) { btnEntrar.addActionListener(l); }
    public void addCriarContaListener(ActionListener l) { btnCriarConta.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}