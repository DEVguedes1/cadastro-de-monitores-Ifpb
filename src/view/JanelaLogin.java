package view;

import javax.swing.JTextField;

public class JanelaLogin extends JanelaPadrao {
	
	private JTextField tfNome;
	private JTextField tfEmail;
	private JTextField tfSenha;
	
	public JanelaLogin() {
		super("Login");
	}

	@Override
	public void desenharJanela() {
		this.addCamposDeTexto();
		setVisible(true);
	}
	
	private void addCamposDeTexto() {
		tfNome = new JTextField();
		tfNome.setBounds(100, 110, 250, 30);
		add(tfNome);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(100, 110, 250, 30);
		add(tfEmail);
		
		tfSenha = new JTextField();
		tfSenha.setBounds(100, 110, 250, 30);
		add(tfSenha);
		
	}
	
	public static void main(String[] args) {
		new JanelaLogin();
	}
	
}
