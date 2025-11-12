package models;

public class Coordenador extends Usuario {
	
	//atributo
	private String nome;
	
	public Coordenador() {
	}

	public Coordenador(String nome, String email, String senha) {
		super(email, senha);
		this.nome = nome;
	}

	// metodos
	public String getNome() {
		return nome;
	}
	
	public void set(String nome) {
		this.nome = nome;
	}
	
	
	
	//+contatarEstudante()
	
}
