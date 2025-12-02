package models;

import java.util.ArrayList;
import java.util.List;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class CentralDeInformacoes {

	private ArrayList<Coordenador> todosOsCoordenadores = new ArrayList<>();
	private ArrayList<Aluno> todosOsAlunos = new ArrayList<>();
	private ArrayList<Edital> todosOsEditais = new ArrayList<>();
	
	public CentralDeInformacoes() {
		this.todosOsCoordenadores = new ArrayList<>();
		this.todosOsAlunos = new ArrayList<>();
		this.todosOsEditais = new ArrayList<>();
	}

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
	
	public ArrayList<Aluno> listarApenasAlunos() {
	    return getTodosOsAlunos();
	}
	
	public boolean emailExiste(String email) {
	    for (Coordenador c : todosOsCoordenadores) {
	        if (c.getEmail().equalsIgnoreCase(email)) return true;
	    }
	    for (Aluno a : todosOsAlunos) {
	        if (a.getEmail().equalsIgnoreCase(email)) return true;
	    }
	    return false;
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
	    }

	    for (Edital e : todosOsEditais) {
	        if (e.getId() == id) {
	            return e;
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
		
			ArrayList<Inscricao> inscricoes = d.getInscricoes();
			if (inscricoes == null) {
				inscricoes = new ArrayList<>();
			}

			for (Inscricao i : inscricoes){
				if (i.getAluno().getMatricula().equals(matricula)){
					inscricoesDoAluno.add(d);
				}
			}
		}

		return inscricoesDoAluno;
	}
	// Dentro de models/CentralDeInformacoes.java


	public boolean adicionarCoordenador(Coordenador c) {
	    if (this.todosOsCoordenadores == null) {
	        this.todosOsCoordenadores = new ArrayList<>();
	    }
	    // Verifica se já existe (opcional, mas recomendado)
	    for (Coordenador existente : todosOsCoordenadores) {
	        if (existente.getEmail().equalsIgnoreCase(c.getEmail())) {
	            return false;
	        }
	    }
	    todosOsCoordenadores.add(c);
	    return true;
	}
	
	public ArrayList<Coordenador> getTodosOsCoordenadores() {
        return todosOsCoordenadores;
    }
	
	public void Login() {
		
	}
}

