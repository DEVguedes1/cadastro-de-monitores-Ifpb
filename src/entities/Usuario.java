package entities;

public class Usuario {

	private String email;
	private String senha;
	
	public Usuario() {
		super();
	}

	public Usuario(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean fazerLogin(String email, String senha) {
		if (this.email.equals(email) && this.senha.equals(senha)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void editarPerfil(String email, String senha) {
		setEmail(email);
		setSenha(senha);
	}
	
}
