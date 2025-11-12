package models;

public class Aluno extends Usuario {
	
	//atributos
	private String nomeDoAluno;
	private String matricula;
	private Double cre;
	
	// construtores
	public Aluno() {
	}
	
	public Aluno(String nomeDoAluno, String matricula, Double cre, String email, String senha) {
		super(email, senha);
		this.nomeDoAluno = nomeDoAluno;
		this.matricula = matricula;
		this.cre = cre;
	}

	//metodos
	
	public String getNomeDoAluno() {
		return nomeDoAluno;
	}
	
	public void setNomeDoAluno(String nomeDoAluno) {
		this.nomeDoAluno = nomeDoAluno;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public Double getCre() {
		return cre;
	}
	
	public void increverMonitoria() {
		
	}
	
	// inscrever monitoria
	
	// desistir Inscricao

	@Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nomeDoAluno + '\'' +
                ", matricula='" + matricula + '\'' +
                ", cre=" + cre +
                ", email='" + getEmail() + '\'' +
                '}';
	}
}
