package models;

/**
 * Representa o administrador do sistema (Professor/Coordenador).
 * <p>
 * Possui privilégios elevados para:
 * <ul>
 * <li>Criar e editar Editais.</li>
 * <li>Gerenciar inscrições.</li>
 * <li>Gerar relatórios e realizar backups.</li>
 * </ul>
 */

import java.util.Scanner;
import models.utils.Validacao;

public class Coordenador extends Usuario {
	
	//atributo
	private String nome;
	
	public Coordenador() {
	}

	public Coordenador(String nome, String email, String senha) {
		super(email, senha);
		this.nome = nome;
	}

	// metodos
	public String getNome() {
		return nome;
	}
	
	public void set(String nome) {
		this.nome = nome;
	}

	public static void cadastrarViaConsole(Scanner sc, CentralDeInformacoes ci) {
        System.out.println("\n--- 10. Cadastrar Novo Coordenador ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        
        Coordenador novoCoord = new Coordenador(nome, email, senha);
        
        boolean sucesso = ci.adicionarCoordenador(novoCoord);
        
        if (sucesso) {
            Persistencia.salvarCentral(ci);
            System.out.println("\n[+] Coordenador cadastrado com sucesso!");
        } else {
            System.err.println("\n[!] Erro: Já existe um coordenador com esse email.");
        }
    }
	
	public static Coordenador cadastrarViaCentral(Scanner sc, CentralDeInformacoes ci) {
        System.out.println("\n--- Cadastrar Novo Coordenador ---");
        
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        
        System.out.print("Email: ");
        String email = sc.nextLine();
        while (!Validacao.validarEmail(email)) {
            System.out.println("Email inválido! Tente novamente.");
            email = sc.nextLine();
        }
        
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        while (!Validacao.senhaForte(senha)) {
            System.out.println("Senha fraca! Use letras, números e especiais:");
            senha = sc.nextLine();
        }
        	
        Coordenador novoCoord = new Coordenador(nome, email, senha);
        
        boolean sucesso = ci.adicionarCoordenador(novoCoord);
        
        if (sucesso) {
            Persistencia.salvarCentral(ci);
            System.out.println("\n[+] Coordenador cadastrado com sucesso!");
        } else {
            System.err.println("\n[!] Erro: Já existe um coordenador com esse email.");
        }
		return novoCoord;
    }
	
	
}
