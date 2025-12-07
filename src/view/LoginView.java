package view;

/**
 * Tela de Autenticação e Boas-vindas.
 * <p>
 * Ponto de entrada da aplicação. Implementa um design "Split Screen" (Tela Dividida):
 * <ul>
 * <li><b>Esquerda (Marca):</b> Logotipo e descrição do sistema sobre fundo colorido.</li>
 * <li><b>Direita (Formulário):</b> Campos de E-mail/Senha e botões de ação.</li>
 * </ul>
 * Gerencia o acesso de Alunos e Coordenadores.
 */

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnCriarConta;

    public LoginView() {
        setTitle("Login");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        // --- PAINEL ESQUERDO (ROXO) ---
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(Cores.ROXO_SIDEBAR);
        painelEsquerdo.setBounds(0, 0, 400, 600);
        painelEsquerdo.setLayout(null);
        add(painelEsquerdo);
        
        // Marca D'água (Fundo)
        JLabel lblSigla = new JLabel("SISMON");
        lblSigla.setFont(new Font("Segoe UI", Font.BOLD, 60));
        lblSigla.setForeground(new Color(255, 255, 255, 20)); // Bem transparente
        lblSigla.setBounds(40, 100, 300, 80);
        painelEsquerdo.add(lblSigla);

        // Título Principal
        JLabel lblTituloApp = new JLabel("<html>SISTEMA DE<br>GESTÃO DE<br>MONITORIA</html>");
        lblTituloApp.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTituloApp.setForeground(Color.WHITE);
        lblTituloApp.setBounds(40, 180, 350, 150);
        painelEsquerdo.add(lblTituloApp);

        JLabel lblDesc = new JLabel("<html>Gerencie editais, inscrições<br>e resultados em um só lugar.</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDesc.setForeground(new Color(220, 220, 255));
        lblDesc.setBounds(40, 340, 300, 50);
        painelEsquerdo.add(lblDesc);

        // --- PAINEL DIREITO (BRANCO) ---
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

        // Inputs
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(Componentes.FONT_SUB);
        lblEmail.setBounds(50, 180, 100, 20);
        painelDireito.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(50, 205, 350, 40);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        painelDireito.add(txtEmail);

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

        // Botões
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBounds(50, 360, 350, 45);
        btnEntrar.setBackground(Cores.ROXO_SIDEBAR);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(Componentes.FONT_BOTAO);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelDireito.add(btnEntrar);

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

    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public void addLoginListener(ActionListener l) { btnEntrar.addActionListener(l); }
    public void addCriarContaListener(ActionListener l) { btnCriarConta.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}