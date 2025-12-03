package models;

import models.recurses.Disciplina;

public class Inscricao {
    private Aluno aluno;
    private Disciplina disciplina;
    private double nota;
    private double CRE;

    public Inscricao(Aluno aluno, Disciplina disciplina, double nota, double CRE) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.CRE = CRE;
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
