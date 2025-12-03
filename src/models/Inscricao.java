package models;

import models.recurses.Disciplina;

public class Inscricao {
    private Aluno aluno;
    private Disciplina disciplina;
    private double nota;
    private double CRE;
    private Situacao situacao;

    public Inscricao(Aluno aluno, Disciplina disciplina, double nota, double CRE) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.CRE = CRE;
        this.situacao = Situacao.CONCORRENDO;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
    public enum Situacao {
        CONCORRENDO,
        APROVADO_BOLSISTA,
        APROVADO_VOLÚNTARIO,
        LISTA_ESPERA,
        DESISTENTE,
        DESCLASSIFICADO
    }
    public double getNotaFinal(){
        return (this.nota * this.disciplina.getPesoNota()) + (this.CRE * this.disciplina.getPesoCRE());
    }

    public Aluno getAluno() { 
        return aluno; 
    }
    public Disciplina getDisciplina() { 
        return disciplina; 
    }
    public double getNota() { 
        return nota; 
    }

    public double getCRE() {
        return CRE;
    }
}
