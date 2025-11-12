package models.recurses;

import java.util.ArrayList;
import models.Aluno; 

public class Disciplina {
	private String nomeDisciplina;
	private int qntdVagas;
	
	private ArrayList<Aluno> alunos = new ArrayList<>();
	
	public Disciplina() {
	}

	public Disciplina(String nomeDisciplina, int qntdVagas) {
		super();
		this.nomeDisciplina = nomeDisciplina;
		this.qntdVagas = qntdVagas;
	}

	public Disciplina(String nomeDisciplina, int qntdVagas, ArrayList<Aluno> alunos) {
		this.nomeDisciplina = nomeDisciplina;
		this.qntdVagas = qntdVagas;
		this.alunos = alunos;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public int getQntdVagas() {
		return qntdVagas;
	}

	public void setQntdVagas(int qntdVagas) {
		this.qntdVagas = qntdVagas;
	}

	public ArrayList<Aluno> getAlunos() {
		if (this.alunos == null) {
			this.alunos = new ArrayList<>();
		}
		return alunos;
	}

	public void setAlunos(Aluno a) {
		if (this.alunos == null) {
			this.alunos = new ArrayList<>();
		}
		alunos.add(a);
	}
}