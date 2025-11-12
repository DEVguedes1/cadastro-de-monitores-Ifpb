package models;

import java.util.ArrayList;
import java.util.List;

import models.recurses.Disciplina;
import models.recurses.Edital;

public class CentralDeInformacoes {

	private ArrayList<Aluno> todosOsAlunos = new ArrayList<>();
	private ArrayList<Edital> todosOsEditais = new ArrayList<>();
	
	public boolean adicionarAluno(Aluno aluno) {
		if (this.todosOsAlunos == null) {
			this.todosOsAlunos = new ArrayList<>();
		}
		
		for (Aluno a: todosOsAlunos) {
			if (a.getMatricula().equals(aluno.getMatricula())) {
				return false;
			}
		}
		todosOsAlunos.add(aluno);
		return true;
	}
	
	public ArrayList<Aluno> getTodosOsAlunos() {
		if (this.todosOsAlunos == null) {
			this.todosOsAlunos = new ArrayList<>();
		}
		return todosOsAlunos;
	}

	public void setTodosOsAlunos(ArrayList<Aluno> todosOsAlunos) {
		this.todosOsAlunos = todosOsAlunos;
	}
	
	public Aluno recuperarAlunoPorMatricula(String matricula) {
		if (this.todosOsAlunos == null) {
			this.todosOsAlunos = new ArrayList<>();
		}
		
		for (Aluno a: todosOsAlunos) {
			if (a.getMatricula().equalsIgnoreCase(matricula)){
				System.out.println("Aluno: "+a.getNomeDoAluno()+", Matricula: "+a.getMatricula());
				return a; 
			}
		}
		return null;
	}
	
	
	public ArrayList<Edital> getTodosOsEditais() {
		if (this.todosOsEditais == null) {
			this.todosOsEditais = new ArrayList<>();
		}
		return todosOsEditais;
	}

	public boolean adicionarEdital(Edital edital) {
		if (this.todosOsEditais == null) {
			this.todosOsEditais = new ArrayList<>();
		}
		
		for (Edital e: todosOsEditais) {
			if (e.getId() == edital.getId()) {
				return false;
			}
		}
		this.todosOsEditais.add(edital);
		return true;
	}
	
	public Edital recuperarEditalPorId(long id) {
		if (this.todosOsEditais == null) {
			this.todosOsEditais = new ArrayList<>();
		
		for (Edital e: todosOsEditais) {
			if (e.getId() == id) {
				System.out.println(e.toString());
				return e;
				}
			}
		}
		return null;
	}
	
	public List<Disciplina> recuperarInscriçõesDeUmAlunoEmUmEdital(String matricula, long idEdital) {
		
		Aluno aluno = this.recuperarAlunoPorMatricula(matricula);
		Edital edital = this.recuperarEditalPorId(idEdital);
		
		if (aluno == null || edital == null) {
			return null; 
		}
		
		ArrayList<Disciplina> inscricoesDoAluno = new ArrayList<>();
		
		ArrayList<Disciplina> disciplinasDoEdital = edital.getDisciplinas();
		if (disciplinasDoEdital == null) {
			disciplinasDoEdital = new ArrayList<>(); 
		}

		for (Disciplina d : disciplinasDoEdital) {
		
			ArrayList<Aluno> alunosDaDisciplina = d.getAlunos();
			if (alunosDaDisciplina == null) {
				alunosDaDisciplina = new ArrayList<>();
			}

			if (alunosDaDisciplina.contains(aluno)) { 
				inscricoesDoAluno.add(d);
			}
		}

		return inscricoesDoAluno;
	}
}

