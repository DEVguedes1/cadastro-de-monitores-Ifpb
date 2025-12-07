package models.recurses;

/**
 * Representa o Edital de Seleção de Monitores.
 * <p>
 * Esta classe é a entidade principal do processo seletivo. Ela define:
 * <ul>
 * <li>O período de vigência (Data Início e Fim).</li>
 * <li>As disciplinas ofertadas e suas vagas.</li>
 * <li>O limite de inscrições por aluno.</li>
 * <li>O controle de status (Ativo, Encerrado, Não Iniciado).</li>
 * </ul>
 * * @author Seu Nome
 * @version 1.0
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.Aluno;
import models.Inscricao;

public class Edital {
	private long id; 
	private String numEdital;
	private LocalDate dataIncio;
	private LocalDate dataFinal;
	private ArrayList<Disciplina> disciplinas;
	private int maxInc;
	private Status status;

	public enum Status{
		ATIVO,
		ENCERRADO,
		NÃO_COMEÇOU
	}
	public Edital() {
		this.id = System.currentTimeMillis(); 
		this.disciplinas = new ArrayList<>(); 
	}
	
	public Edital(String numEdital, LocalDate dataInicio, LocalDate dataFinal, ArrayList<Disciplina> disciplinas, int maxInc) {
		LocalDate hoje = LocalDate.now();
		if (hoje.isBefore(dataInicio)){
			this.status = Status.NÃO_COMEÇOU;
		}
		else{
			this.status = Status.ATIVO;
		}
		this.id = System.currentTimeMillis();
		this.numEdital = numEdital;
		this.dataIncio = dataInicio;
		this.dataFinal = dataFinal;
		this.disciplinas = disciplinas;
		this.maxInc = maxInc;
	}

	public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
	
	/**
     * Realiza a inscrição de um aluno em uma disciplina deste edital.
     * <p>
     * <b>Regras de Negócio:</b>
     * <ol>
     * <li>Verifica se a data atual está dentro do prazo do edital.</li>
     * <li>Verifica se a disciplina realmente pertence a este edital.</li>
     * <li>Registra a nota informada pelo aluno.</li>
     * </ol>
     *
     * @param a O aluno que está se inscrevendo.
     * @param disciplina A disciplina alvo.
     * @param notaDisciplina A nota obtida pelo aluno na matéria.
     * @return {@code true} se a inscrição foi realizada com sucesso; {@code false} se estiver fora do prazo ou disciplina inválida.
     */

	public boolean inscrever(Aluno a, Disciplina disciplina, double notaDisciplina) {
		LocalDate hoje = LocalDate.now();
		boolean dentroDoPrazo = (!hoje.isBefore(this.dataIncio)) && (!hoje.isAfter(this.dataFinal));

		if (this.disciplinas == null) {
			this.disciplinas = new ArrayList<>();
		}

		boolean disciplinaValida = this.disciplinas.contains(disciplina);
		
		if (dentroDoPrazo && disciplinaValida) {
			disciplina.setInscricoes(new Inscricao(a, disciplina, notaDisciplina, a.getCre()));
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
	
/**
     * Cria uma cópia profunda (Deep Copy) deste edital.
     * <p>
     * Utilizado para agilizar o cadastro de novos processos seletivos baseados em anteriores.
     * O novo edital herda:
     * <ul>
     * <li>Nome (com sufixo "Cópia").</li>
     * <li>Configurações de vagas e pesos.</li>
     * <li>Lista de disciplinas.</li>
     * </ul>
     * <b>Importante:</b> A lista de alunos inscritos é zerada na cópia.
     *
     * @return Uma nova instância de Edital pronta para edição.
     */
	
	public Edital clonarEdital() {
        // Gera um novo número sugerido
        String novoNumero = this.numEdital + " (Cópia)";
        
        // Define datas padrão (hoje e amanhã) para o coordenador editar depois
        LocalDate novaDataInicio = LocalDate.now();
        LocalDate novaDataFim = LocalDate.now().plusDays(30);
        
        // Clona as disciplinas (IMPORTANTE: Sem as inscrições!)
        ArrayList<Disciplina> novasDisciplinas = new ArrayList<>();
        if (this.disciplinas != null) {
            for (Disciplina d : this.disciplinas) {
                // Cria uma nova disciplina com os mesmos dados da antiga
                Disciplina novaDisc = new Disciplina(
                    d.getNomeDisciplina(), 
                    d.getQntdVagas(), 
                    d.getPesoNota(), 
                    d.getPesoCRE(), 
                    d.getDocente(), 
                    d.getPeriodo()
                );
                // Inicia com lista de inscritos vazia
                novaDisc.setInscricoes(new ArrayList<>()); 
                novasDisciplinas.add(novaDisc);
            }
        }

        // Cria o novo objeto Edital
        Edital clone = new Edital(novoNumero, novaDataInicio, novaDataFim, novasDisciplinas, this.maxInc);
        
        // O construtor define o ID automaticamente como System.currentTimeMillis(), 
        // então o clone terá um ID único.
        
        return clone;
    }
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		sb.append("Edital de Monitoria ").append(this.numEdital).append("-\n");
		sb.append("Data de ínicio: " + this.getDataIncio().format(formatoBR) );
		sb.append("/ Data final: " + this.getDataFinal().format(formatoBR) );
		sb.append("\n#Disciplinas\n");
		
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

		//String status = this.jaAcabou();
		sb.append("Situação: ").append(status).append(".");		
		return sb.toString();
	}

    public int getMaxInc() {
        return maxInc;
    }

    public void setMaxInc(int maxInc) {
        this.maxInc = maxInc;
    }
	public Disciplina buscarDisciplina(String nomeDisciplina){
		for (Disciplina dis : this.getDisciplinas()){
			if (dis.getNomeDisciplina().equals(nomeDisciplina)){
				return dis;
			}
		}
		return null;
	}
}

