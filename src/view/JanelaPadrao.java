package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class JanelaPadrao extends JFrame {

	public static final Font FONTE_PROJETO = new Font("Arial", Font.BOLD, 14);

	public abstract void desenharJanela();
	
	public JanelaPadrao(String titulo) {
		ImageIcon icone = new ImageIcon("if.png");
		this.setIconImage(icone.getImage());
		this.setTitle(titulo);
		
		this.configurarJanela();		
		this.criarCabecalho(titulo);	
	}

	private void configurarJanela() {
		this.setSize(400, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		setLocationRelativeTo(null);
		this.desenharJanela();
	}

	private void criarCabecalho(String titulo) {
		JLabel lbMeuNome = new JLabel(titulo);
		lbMeuNome.setToolTipText("Esse é um protótipo desenvolvido pelo professor");
		lbMeuNome.setFont(new Font("Courier New", Font.BOLD, 36));
		lbMeuNome.setBounds(0, 30, 400, 50);
		lbMeuNome.setOpaque(true);
		lbMeuNome.setHorizontalAlignment(JLabel.CENTER);
		lbMeuNome.setBackground(Color.BLACK);
		lbMeuNome.setForeground(Color.RED);
		this.add(lbMeuNome);
	}
	
	public static void main(String[] args) {
//		JanelaPadrao janela = new JanelaPadrao();
	}

}
