package view;

/**
 * Formulário de Registro de Coordenador.
 * <p>
 * Tela administrativa para cadastro de novos gestores do sistema.
 */

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

public class CadastroCoordenadorView extends JFrame { // Herda JFrame (Sem Sidebar)

    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar, btnVoltar;

    public CadastroCoordenadorView() {
        setTitle("Novo Coordenador");
        setSize(900, 600); // Widescreen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        inicializar();
    }

    private void inicializar() {
        // --- PAINEL ESQUERDO (DESTAQUE) ---
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(Cores.ROXO_SIDEBAR);
        painelEsquerdo.setBounds(0, 0, 350, 600);
        painelEsquerdo.setLayout(null);
        add(painelEsquerdo);

        JLabel lblLogo = new JLabel("NOVA CONTA");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(40, 200, 300, 40);
        painelEsquerdo.add(lblLogo);

        JLabel lblDesc = new JLabel("<html>Cadastro Administrativo<br>para Coordenadores</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblDesc.setForeground(new Color(230, 230, 255));
        lblDesc.setBounds(40, 250, 300, 60);
        painelEsquerdo.add(lblDesc);

        // --- PAINEL DIREITO (FORMULÁRIO) ---
        JPanel painelDireito = new JPanel();
        painelDireito.setBackground(Color.WHITE);
        painelDireito.setBounds(350, 0, 550, 600);
        painelDireito.setLayout(null);
        add(painelDireito);

        JLabel lblTitulo = new JLabel("Preencha seus dados");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setBounds(50, 50, 400, 40);
        painelDireito.add(lblTitulo);

        // Campos
        addLabel(painelDireito, "Nome Completo", 120);
        txtNome = addCampo(painelDireito, 145);

        addLabel(painelDireito, "E-mail Institucional", 200);
        txtEmail = addCampo(painelDireito, 225);

        addLabel(painelDireito, "Senha de Acesso", 280);
        txtSenha = new JPasswordField();
        txtSenha.setBounds(50, 305, 400, 40);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(5,10,5,10)));
        painelDireito.add(txtSenha);

        // Botões
        btnCadastrar = new JButton("CADASTRAR COORDENADOR");
        btnCadastrar.setBounds(50, 380, 400, 50);
        btnCadastrar.setBackground(Cores.AZUL); // Azul
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(Componentes.FONT_BOTAO);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);
        painelDireito.add(btnCadastrar);

        btnVoltar = new JButton("Voltar ao Login");
        btnVoltar.setBounds(50, 440, 400, 40);
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(Color.GRAY);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVoltar.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        btnVoltar.setFocusPainted(false);
        painelDireito.add(btnVoltar);
    }

    // Auxiliares
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
    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public void addAcaoCadastrar(ActionListener l) { btnCadastrar.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltar.addActionListener(l); }
    public void mostrarMensagem(String msg) { JOptionPane.showMessageDialog(this, msg); }
}