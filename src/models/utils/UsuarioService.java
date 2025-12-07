package models.utils;

/**
 * Serviço de Regras de Negócio para Usuários.
 * <p>
 * Atua como uma camada intermediária entre a Interface Gráfica (View/Controller)
 * e o Banco de Dados (CentralDeInformacoes).
 * <p>
 * Responsabilidades:
 * <ul>
 * <li>Verificar credenciais de login.</li>
 * <li>Checar se é o primeiro acesso ao sistema (sem coordenador).</li>
 * <li>Gerenciar a lógica de autenticação.</li>
 * </ul>
 */

import models.CentralDeInformacoes;
import models.Coordenador;
import models.Aluno;
import models.Persistencia;
import models.UsuarioLogado;
import java.util.ArrayList;

public class UsuarioService {

    /**
     * Verifica se existe pelo menos um Coordenador cadastrado no sistema.
     * <p>
     * Utilizado na inicialização (Main) para decidir se abre a tela de Login
     * ou a tela de "Primeiro Acesso" para cadastro do administrador.
     * * @return {@code true} se houver coordenador, {@code false} se o sistema estiver vazio.
     */

    public boolean existeCoordenador() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        // Proteção contra lista nula
        if (central.getTodosOsCoordenadores() == null) return false;
        return !central.getTodosOsCoordenadores().isEmpty();
    }

    /**
     * Realiza a autenticação do usuário.
     * * @param email Email fornecido.
     * @param senha Senha fornecida.
     * @return Um objeto {@link UsuarioLogado} se sucesso, ou {@code null} se falhar.
     */

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