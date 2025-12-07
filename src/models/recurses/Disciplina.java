package models.recurses;

/**
 * Representa uma Matéria/Disciplina ofertada em um edital.
 * <p>
 * Responsável por:
 * <ul>
 * <li>Definir a quantidade de vagas (Remuneradas/Voluntárias).</li>
 * <li>Armazenar os pesos de avaliação (Peso Nota vs Peso CRE).</li>
 * <li>Processar a lista de classificação dos alunos inscritos.</li>
 * </ul>
 */

import java.util.ArrayList;
import models.Inscricao; 


public class Disciplina {
	private String nomeDisciplina;
	private int qntdVagas;
	private float pesoNota;
	private float pesoCRE;
	private String docente;
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

	public Disciplina(String nomeDisciplina, int qntdVagas, float pesoNota, float pesoCRE, String docente, int periodo) {
		super();
		this.nomeDisciplina = nomeDisciplina;
		this.qntdVagas = qntdVagas;
		this.pesoCRE = pesoCRE;
		this.pesoNota = pesoNota;
		this.docente = docente;
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

    public String getDocente() {
        return docente;
    }

    public void setDocente(String doscente) {
        this.docente = doscente;
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
    
    // Ele define a LISTA INTEIRA (usado para limpar no clone)
    public void setInscricoes(ArrayList<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }
    
	/**
     * Busca uma inscrição específica dentro desta disciplina pelo nome do aluno.
     * * @param d A disciplina onde a busca será feita.
     * @param nomeAluno O nome do aluno a ser buscado.
     * @return O objeto {@link Inscricao} encontrado ou {@code null} se não existir.
     */

	public static Inscricao buscarInscricao(Disciplina d, String nomeAluno){
		for (Inscricao i : d.getInscricoes()){
			if (i.getAluno().getNomeDoAluno().equals(nomeAluno)){
				return i;
			}
		}
		return null;
	}
	
	/**
     * Algoritmo de Classificação e Seleção.
     * <p>
     * Este método executa o processamento final da disciplina:
     * <ol>
     * <li>Ordena a lista de inscrições da maior nota final para a menor.</li>
     * <li>Percorre a lista ordenada.</li>
     * <li>Atribui o status {@code APROVADO_BOLSISTA} para os primeiros 'N' alunos (onde N é a quantidade de vagas).</li>
     * <li>Atribui o status {@code LISTA_ESPERA} para os demais.</li>
     * </ol>
     */
	
	public void calcularResultadoFinal() {
        if (this.inscricoes == null || this.inscricoes.isEmpty()) return;

        // 1. Ordenar a lista (Do maior NotaFinal para a menor)
        this.inscricoes.sort((a, b) -> Double.compare(b.getNotaFinal(), a.getNotaFinal()));

        // 2. Definir os Status
        int vagasDisponiveis = this.qntdVagas;
        
        for (int i = 0; i < inscricoes.size(); i++) {
            Inscricao insc = inscricoes.get(i);
            
            // Só altera quem ainda está concorrendo (ignora quem já desistiu)
            if (insc.getSituacao() == models.Inscricao.Situacao.CONCORRENDO || 
                insc.getSituacao() == models.Inscricao.Situacao.LISTA_ESPERA ||
                insc.getSituacao() == models.Inscricao.Situacao.APROVADO_BOLSISTA) {
                
                if (i < vagasDisponiveis) {
                    insc.setSituacao(models.Inscricao.Situacao.APROVADO_BOLSISTA);
                } else {
                    insc.setSituacao(models.Inscricao.Situacao.LISTA_ESPERA);
                }
            }
        }
    }

}