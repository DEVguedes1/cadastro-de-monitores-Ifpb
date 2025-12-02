package models.recurses;

import java.util.ArrayList;
import models.Aluno; 

public class Disciplina {
	private String nomeDisciplina;
	private int qntdVagas;
	private float pesoNota;
	private float pesoCRE;
	private String doscente;
	private int periodo;
	public float getPesoNota() {
		return pesoNota;
	}

	public void setPesoNota(float pesoNota) {
		this.pesoNota = pesoNota;
	}
	
	public void setPesoCRE(float pesoCRE) {
		this.pesoCRE = pesoCRE;
	}

	private ArrayList<Aluno> alunos = new ArrayList<>();
	
	public Disciplina() {
	}

	public Disciplina(String nomeDisciplina, int qntdVagas, float pesoNota, float pesoCRE, String doscente, int periodo) {
		super();
		this.nomeDisciplina = nomeDisciplina;
		this.qntdVagas = qntdVagas;
		this.pesoCRE = pesoCRE;
		this.pesoNota = pesoNota;
		this.doscente = doscente;
		this.periodo = periodo;

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

    public float getPesoCRE() {
        return pesoCRE;
    }

    public String getDoscente() {
        return doscente;
    }

    public void setDoscente(String doscente) {
        this.doscente = doscente;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
}