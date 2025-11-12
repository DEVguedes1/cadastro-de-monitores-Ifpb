package controller;

import models.Aluno;
import models.Usuario; // Import necessário se usar polimorfismo
import models.recurses.Disciplina;
import models.recurses.Edital;

import java.util.ArrayList;
import java.util.List;

public class EditalController {

    private List<Edital> editais = new ArrayList<>();

    public void publicarEdital(Edital edital) {
        editais.add(edital);
        System.out.println("Edital publicado com sucesso! ID: " + edital.getId());
    }

    public List<Edital> listarEditais() {
        return editais;
    }

    public Edital buscarEditalPorId(long id) {
        return editais.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean inscreverAluno(long idEdital, Aluno aluno, String nomeDisciplina) {
        Edital edital = buscarEditalPorId(idEdital);
        if (edital == null) {
            System.out.println("Edital não encontrado.");
            return false;
        }
        
        // Busca a disciplina dentro do edital
        Disciplina disciplinaAlvo = edital.getDisciplinas().stream()
                .filter(d -> d.getNomeDisciplina().equalsIgnoreCase(nomeDisciplina))
                .findFirst()
                .orElse(null);

        if (disciplinaAlvo == null) {
            System.out.println("Disciplina não encontrada neste edital.");
            return false;
        }

        return edital.inscrever(aluno, disciplinaAlvo);
    }
}