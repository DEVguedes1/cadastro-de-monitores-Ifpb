package models;

/**
 * Gerenciador de Persistência em Memória (Repositório Estático).
 * <p>
 * Atua como um Banco de Dados temporário durante a execução.
 * Possui listas estáticas globais para armazenar:
 * <ul>
 * <li>Todos os Alunos.</li>
 * <li>Todos os Coordenadores.</li>
 * <li>Todos os Editais.</li>
 * </ul>
 * Oferece métodos CRUD (Create, Read, Update, Delete) simplificados.
 */

import java.util.ArrayList;
import java.util.List;
import models.recurses.Disciplina;
import models.recurses.Edital;

public class CentralStatic {

    private static final ArrayList<Coordenador> todosOsCoordenadores = new ArrayList<>();
    private static final ArrayList<Aluno> todosOsAlunos = new ArrayList<>();
    private static final ArrayList<Edital> todosOsEditais = new ArrayList<>();

    // Alunos
    public static boolean adicionarAluno(Aluno aluno) {
        for (Aluno a: todosOsAlunos) {
            if (a.getMatricula().equals(aluno.getMatricula())) {
                return false;
            }
        }
        todosOsAlunos.add(aluno);
        return true;
    }

    public static ArrayList<Aluno> getTodosOsAlunos() {
        return todosOsAlunos;
    }

    public static Aluno recuperarAlunoPorMatricula(String matricula) {
        for (Aluno a: todosOsAlunos) {
            if (a.getMatricula().equalsIgnoreCase(matricula)){
                return a; 
            }
        }
        return null;
    }

    // Coordenadores
    public static boolean adicionarCoordenador(Coordenador c) {
        for (Coordenador existente : todosOsCoordenadores) {
            if (existente.getEmail().equalsIgnoreCase(c.getEmail())) {
                return false;
            }
        }
        todosOsCoordenadores.add(c);
        return true;
    }

    public static ArrayList<Coordenador> getTodosOsCoordenadores() {
        return todosOsCoordenadores;
    }

    // Editais
    public static boolean adicionarEdital(Edital edital) {
        for (Edital e: todosOsEditais) {
            if (e.getId() == edital.getId()) {
                return false;
            }
        }
        todosOsEditais.add(edital);
        return true;
    }

    public static ArrayList<Edital> getTodosOsEditais() {
        return todosOsEditais;
    }

    public static Edital recuperarEditalPorId(long id) {
        for (Edital e : todosOsEditais) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
    
    /**
     * Recupera todas as inscrições feitas por um aluno específico em um edital.
     * <p>
     * Realiza um cruzamento de dados:
     * 1. Busca o Edital pelo ID.
     * 2. Percorre todas as disciplinas desse edital.
     * 3. Verifica se a matrícula do aluno consta na lista de inscritos da disciplina.
     *
     * @param matricula A matrícula do aluno.
     * @param idEdital O ID do edital alvo.
     * @return Uma lista de disciplinas nas quais o aluno se inscreveu.
     */

    public static List<Disciplina> recuperarInscricoesDeUmAlunoEmUmEdital(String matricula, long idEdital) {
        Aluno aluno = recuperarAlunoPorMatricula(matricula);
        Edital edital = recuperarEditalPorId(idEdital);
        if (aluno == null || edital == null) return null;

            ArrayList<Disciplina> inscricoesDoAluno = new ArrayList<>();
            ArrayList<Disciplina> disciplinasDoEdital = edital.getDisciplinas();
        if (disciplinasDoEdital == null) disciplinasDoEdital = new ArrayList<>();

        for (Disciplina d : disciplinasDoEdital) {

        // Recupera as inscrições da disciplina
            ArrayList<Inscricao> inscricoes = d.getInscricoes();
            if (inscricoes == null) inscricoes = new ArrayList<>();

        // Procura uma inscrição onde o aluno seja o mesmo
        for (Inscricao i : inscricoes) {
            if (i.getAluno().getMatricula().equals(matricula)) {
                inscricoesDoAluno.add(d);
                break; // evita adicionar a disciplina duas vezes
            }
        }
    }

    return inscricoesDoAluno;
}
    
    /**
     * Verifica se um e-mail já está cadastrado no sistema.
     * <p>
     * Varre tanto a lista de Coordenadores quanto a de Alunos para garantir
     * a unicidade do e-mail (Login).
     *
     * @param email O e-mail a ser verificado.
     * @return {@code true} se o e-mail já existe, {@code false} caso contrário.
     */
    
    public static boolean emailExiste(String email) {
        for (Coordenador c : todosOsCoordenadores) {
            if (c.getEmail().equalsIgnoreCase(email)) return true;
        }
        for (Aluno a : todosOsAlunos) {
            if (a.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }
}
