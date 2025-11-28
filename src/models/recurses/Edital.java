package models.recurses;

import java.time.LocalDate;
import java.util.ArrayList;
import models.Aluno;

public class Edital {
	private long id; 
	private String numEdital;
	private LocalDate dataIncio;
	private LocalDate dataFinal;
	private ArrayList<Disciplina> disciplinas;
	private boolean ativo;
	
	public Edital() {
		this.id = System.currentTimeMillis(); 
		this.disciplinas = new ArrayList<>(); 
	}
	
	public Edital(String numEdital, LocalDate dataIncio, LocalDate dataFinal, ArrayList<Disciplina> disciplinas) {
		this.id = System.currentTimeMillis();
		this.numEdital = numEdital;
		this.dataIncio = dataIncio;
		this.dataFinal = dataFinal;
		this.disciplinas = disciplinas;
		this.ativo = true;
	}

	public long getId() {
		return id;
	}

	public String getNumEdital() {
		return numEdital;
	}

	public void setNumEdital(String numEdital) {
		this.numEdital = numEdital;
	}

	public LocalDate getDataIncio() {
		return dataIncio;
	}

	public void setDataIncio(LocalDate dataIncio) {
		this.dataIncio = dataIncio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}	

	public ArrayList<Disciplina> getDisciplinas() {
		if (this.disciplinas == null) {
			this.disciplinas = new ArrayList<>();
		}
		return disciplinas;
	}

	public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public boolean inscrever(Aluno a, Disciplina disciplina) {
		LocalDate hoje = LocalDate.now();
		boolean dentroDoPrazo = (!hoje.isBefore(this.dataIncio)) && (!hoje.isAfter(this.dataFinal));

		if (this.disciplinas == null) {
			this.disciplinas = new ArrayList<>();
		}

		boolean disciplinaValida = this.disciplinas.contains(disciplina);
		
		if (dentroDoPrazo && disciplinaValida) {
			disciplina.setAlunos(a);
			return true;	
		}else {
			if (!dentroDoPrazo) {
				System.out.println("Erro: Inscrição fora do prazo. Período: " + this.dataIncio + " a " + this.dataFinal);
			}
			if (!disciplinaValida) {
				System.out.println("Erro: A disciplina " + disciplina.getNomeDisciplina() + " não faz parte deste edital.");
			}
			return false;
		}
		
	}
	public void encerrarEdital(){
		this.ativo = false;
	}
	public boolean reabrirEdital(){
		if (this.jaAcabou()){
			return false;
		}
		else {
			this.ativo = true;
			return true;
		}
	}
	public boolean jaAcabou() {
		LocalDate hoje = LocalDate.now();
		boolean dentroDoPrazo = (!hoje.isBefore(this.dataIncio)) && (!hoje.isAfter(this.dataFinal));
		if (!dentroDoPrazo) {
			this.ativo = false;
			System.out.println("O prazo deste edital já foi finalizado");
			return true;
		}else {
			System.out.println("inscrições abertas");
			return false;
		}
	}
	public Edital clonarEdital(){
		return new Edital(this.numEdital + ".2", this.dataIncio, this.dataFinal, this.disciplinas);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Edital de Monitoria ").append(this.numEdital).append("-\n");
		
		sb.append("#Disciplinas\n");
		
		if (this.getDisciplinas() != null && !this.getDisciplinas().isEmpty()) {
			for (Disciplina disc : this.getDisciplinas()) {
				
				sb.append(disc.getNomeDisciplina())
				  .append("-")
				  .append(disc.getQntdVagas())
				  .append(" vagas\n");
			}
		} else {
			sb.append("Nenhuma disciplina cadastrada neste edital.\n");
		}

		String status = this.jaAcabou() ? "encerradas" : "abertas";
		sb.append("Inscrições ").append(status).append(".");		
		
		return sb.toString();
	}

    public boolean isAtivo() {
        return ativo;
    }
}

