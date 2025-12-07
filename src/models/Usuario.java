package models;

/**
 * Classe base abstrata para todos os usuários do sistema.
 * <p>
 * Define os atributos comuns de autenticação (Email e Senha) que são
 * compartilhados por {@link Aluno} e {@link Coordenador}.
 * * @see Aluno
 * @see Coordenador
 */

public class Usuario {

	private String email;
	private String senha;
	
	public Usuario() {
	}
	
	public Usuario(String email, String senha) {
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
	
	/**
	 * Atualiza as credenciais de acesso do usuário.
	 * * @param novoEmail O novo endereço de e-mail.
	 * @param novaSenha A nova senha de acesso.
	 */

	public void editarPerfil(String novoEmail, String novaSenha) {
        this.email = novoEmail;
        this.senha = novaSenha;
    }
}
