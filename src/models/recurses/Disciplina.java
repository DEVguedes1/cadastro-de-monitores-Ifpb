package models.recurses;

import java.util.ArrayList;
import models.Inscricao; 

public class Disciplina {
	private String nomeDisciplina;
	private int qntdVagas;
	private float pesoNota;
	private float pesoCRE;
	private String doscente;
	private int periodo;
	private ArrayList<Inscricao> inscricoes = new ArrayList<>();

	public float getPesoNota() {
		return pesoNota;
	}

	public void setPesoNota(float pesoNota) {
		this.pesoNota = pesoNota;
	}
	
	public void setPesoCRE(float pesoCRE) {
		this.pesoCRE = pesoCRE;
	}
	
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

	public Disciplina(String nomeDisciplina, int qntdVagas, ArrayList<Inscricao> inscricoes) {
		this.nomeDisciplina = nomeDisciplina;
		this.qntdVagas = qntdVagas;
		this.inscricoes = inscricoes;
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

    public ArrayList<Inscricao> getInscricoes() {
		if (this.inscricoes == null){
			this.inscricoes = new ArrayList<>();
		}
        return inscricoes;
    }

    public void setInscricoes(Inscricao inscricao) {
        if (this.inscricoes == null){
			this.inscricoes = new ArrayList<>();
		}
        inscricoes.add(inscricao);
    }
}