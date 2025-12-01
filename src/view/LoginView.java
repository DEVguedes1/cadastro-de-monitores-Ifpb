package view;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

public class LoginView extends JanelaPadrao {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginView() {
        super("Login do Sistema", 400, 350); // Janela um pouco maior
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 14);

        // Título
        JLabel lblTitulo = new JLabel("BEM-VINDO");
        lblTitulo.setForeground(COR_TEXTO);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 400, 40);
        add(lblTitulo);

        // Label Email
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setForeground(COR_TEXTO);
        lblEmail.setFont(fonteLabel);
        lblEmail.setBounds(50, 80, 100, 20);
        add(lblEmail);

        // Campo Email
        txtEmail = new JTextField();
        txtEmail.setBounds(50, 105, 285, 30);
        txtEmail.setFont(fonteCampo);
        txtEmail.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Remove borda 3D
        add(txtEmail);

        // Label Senha
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setForeground(COR_TEXTO);
        lblSenha.setFont(fonteLabel);
        lblSenha.setBounds(50, 150, 100, 20);
        add(lblSenha);

        // Campo Senha
        txtSenha = new JPasswordField();
        txtSenha.setBounds(50, 175, 285, 30);
        txtSenha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(txtSenha);

        // Botão Estilizado
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBounds(50, 240, 285, 40);
        btnEntrar.setBackground(COR_BOTAO);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false); // Remove a linha de foco feia
        btnEntrar.setBorderPainted(false); // Remove borda
        add(btnEntrar);
    }

    public String getEmail() {
        return txtEmail.getText();
    }

    public String getSenha() {
        return new String(txtSenha.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnEntrar.addActionListener(listener);
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}