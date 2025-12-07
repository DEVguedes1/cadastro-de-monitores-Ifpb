package models.utils;

import models.CentralDeInformacoes;
import models.Coordenador;
import models.Aluno;
import models.Persistencia;
import models.UsuarioLogado;
import java.util.ArrayList;

public class UsuarioService {

    public boolean existeCoordenador() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        // Proteção contra lista nula
        if (central.getTodosOsCoordenadores() == null) return false;
        return !central.getTodosOsCoordenadores().isEmpty();
    }

    public UsuarioLogado autenticar(String email, String senha) {
        System.out.println("--- INICIANDO AUTENTICAÇÃO ---");
        System.out.println("Tentando logar com -> Email: [" + email + "] | Senha: [" + senha + "]");

        CentralDeInformacoes central = Persistencia.recuperarCentral();
        
        // 1. Debug e Proteção para Coordenadores
        ArrayList<Coordenador> listaCoords = central.getTodosOsCoordenadores();
        if (listaCoords == null) {
            System.out.println("ERRO CRÍTICO: A lista de coordenadores está NULA.");
            listaCoords = new ArrayList<>();
        }
        System.out.println("Total de Coordenadores no banco: " + listaCoords.size());

        for (Coordenador c : listaCoords) {
            System.out.println("  Verificando contra -> " + c.getEmail() + " | senha: " + c.getSenha());
            
            // Verifica email e senha (com trim para ignorar espaços acidentais)
            if (c.getEmail().trim().equalsIgnoreCase(email.trim()) && 
                c.getSenha().trim().equals(senha.trim())) {
                
                System.out.println("  >>> SUCESSO: Coordenador encontrado!");
                return new UsuarioLogado(c.getNome(), "COORDENADOR");
            }
        }

        // 2. Debug e Proteção para Alunos
        ArrayList<Aluno> listaAlunos = central.getTodosOsAlunos();
        if (listaAlunos == null) listaAlunos = new ArrayList<>();
        System.out.println("Total de Alunos no banco: " + listaAlunos.size());

        for (Aluno a : listaAlunos) {
            if (a.getEmail().trim().equalsIgnoreCase(email.trim()) && 
                a.getSenha().trim().equals(senha.trim())) {
                
                System.out.println("  >>> SUCESSO: Aluno encontrado!");
                return new UsuarioLogado(a.getNomeDoAluno(), "ALUNO");
            }
        }

        System.out.println("--- FALHA: NENHUM USUÁRIO ENCONTRADO ---");
        return null; 
    }
}