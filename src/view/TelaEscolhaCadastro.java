package view;

/**
 * Diálogo Modal para Seleção de Tipo de Conta.
 * <p>
 * Esta janela é um passo intermediário entre a tela de Login e os formulários de cadastro.
 * Aparece quando o usuário clica em "Criar nova conta".
 */

import javax.swing.*;
import view.style.Cores;
import view.style.Componentes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

public class TelaEscolhaCadastro extends JDialog {

    private JButton btnAluno, btnCoordenador;

    /**
     * Construtor do Diálogo.
     * Configura o layout limpo (Fundo branco) e os botões coloridos de seleção.
     * * @param parent A janela pai (geralmente LoginView) que será bloqueada enquanto este diálogo estiver aberto.
     */
    public TelaEscolhaCadastro(JFrame parent) {
        super(parent, "Nova Conta", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE); // Fundo Branco

        JLabel lblTitulo = new JLabel("Quem é você?");
        lblTitulo.setFont(Componentes.FONT_TITULO);
        lblTitulo.setForeground(Cores.TEXTO_ESCURO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 30, 450, 40);
        add(lblTitulo);
        
        JLabel lblSub = new JLabel("Selecione o tipo de perfil para continuar");
        lblSub.setFont(Componentes.FONT_SUB);
        lblSub.setForeground(Color.GRAY);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        lblSub.setBounds(0, 70, 450, 20);
        add(lblSub);

        // Botão ALUNO (Verde)
        btnAluno = new JButton("SOU ALUNO");
        btnAluno.setBounds(50, 120, 350, 50);
        btnAluno.setBackground(Cores.VERDE);
        btnAluno.setForeground(Color.WHITE);
        btnAluno.setFont(Componentes.FONT_BOTAO);
        btnAluno.setFocusPainted(false);
        btnAluno.setBorderPainted(false);
        add(btnAluno);

        // Botão COORDENADOR (Azul)
        btnCoordenador = new JButton("SOU COORDENADOR");
        btnCoordenador.setBounds(50, 190, 350, 50);
        btnCoordenador.setBackground(Cores.AZUL);
        btnCoordenador.setForeground(Color.WHITE);
        btnCoordenador.setFont(Componentes.FONT_BOTAO);
        btnCoordenador.setFocusPainted(false);
        btnCoordenador.setBorderPainted(false);
        add(btnCoordenador);
    }

    /**
     * Define a ação a ser executada quando o botão "SOU ALUNO" for clicado.
     * * @param l O ouvinte de evento (ActionListener do Controller) que tratará o clique.
     */

    public void addAcaoAluno(ActionListener l) { btnAluno.addActionListener(l); }

    /**
     * Define a ação a ser executada quando o botão "SOU COORDENADOR" for clicado.
     * * @param l O ouvinte de evento (ActionListener do Controller) que tratará o clique.
     */
    
    public void addAcaoCoordenador(ActionListener l) { btnCoordenador.addActionListener(l); }
}