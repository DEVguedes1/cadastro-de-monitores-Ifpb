package models;

/**
 * Representa um estudante cadastrado no sistema.
 * <p>
 * Além das credenciais de {@link Usuario}, o aluno possui dados acadêmicos
 * como Matrícula, CRE (Coeficiente de Rendimento Escolar) e Sexo.
 * É a entidade que realiza inscrições nos editais.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Aluno extends Usuario {
	
	//atributos
	private String nomeDoAluno;
	private String matricula;
	private Double cre;
	private Sexo sexo;

	// construtores
	public Aluno() {
	}
	
	public Aluno(String nomeDoAluno, String matricula, Double cre,String email,String senha,Sexo sexo) {
		setEmail(email);
		setSenha(senha);
		this.nomeDoAluno = nomeDoAluno;
		this.matricula = matricula;
		this.cre = cre;
		this.sexo = sexo;
	}

	//metodos
	
	public String getNomeDoAluno() {
		return nomeDoAluno;
	}
	
	public void setNomeDoAluno(String nomeDoAluno) {
		this.nomeDoAluno = nomeDoAluno;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public Double getCre() {
		return cre;
	}
	
	public void setCre(Double cre) {
		this.cre = cre;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public static void cadastrarViaConsole(Scanner sc, CentralDeInformacoes ci) {
        System.out.println("\n--- 1. Cadastrar Novo Aluno ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        
        System.out.print("Email: ");
        String email = sc.nextLine();
        
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();

        System.out.print("CRE: ");
        double cre = 0.0;
        try {
            cre = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("[!] Valor inválido para CRE. Definindo como 0.0");
        }

        System.out.print("Sexo (MASCULINO / FEMININO): ");
        String sexo = sc.nextLine().toUpperCase();
        Sexo sx = null;
        try {
            sx = Sexo.valueOf(sexo);
        } catch(IllegalArgumentException e) {
            System.err.println("[!] Erro: Sexo inválido.");
        }
        
        if (sx != null) {
            Aluno aluno = new Aluno(nome, matricula, cre, email, senha, sx);
            
            boolean sucesso = ci.adicionarAluno(aluno); 
            if (sucesso) {
                Persistencia.salvarCentral(ci);
                System.out.println("\n[+] Aluno cadastrado com sucesso!");
            } else {
                System.err.println("\n[!] Erro: Já existe um aluno com esta matrícula.");
            }
        }
    }

    public static void listarViaConsole(CentralDeInformacoes ci) {
        System.out.println("\n--- 2. Listar Todos os Alunos ---");
        ArrayList<Aluno> alunos = ci.getTodosOsAlunos();
        
        if (alunos.isEmpty()) {
            System.out.println("\nNenhum aluno cadastrado.");
        } else {
            System.out.println();
            for(Aluno aluno: alunos) {
                System.out.println("- Matrícula: " + aluno.getMatricula() + " | Nome: " + aluno.getNomeDoAluno());
            }
        }
    }

    public static void buscarPorMatriculaViaConsole(Scanner sc, CentralDeInformacoes ci) {
        System.out.println("\n--- 3. Buscar Aluno por Matrícula ---");
        System.out.print("Digite a matrícula: ");
        String matricula = sc.nextLine();
        
        Aluno aluno = ci.recuperarAlunoPorMatricula(matricula);
        if (aluno != null) {
            System.out.println("\n[Aluno Encontrado]");
            System.out.println("  Nome: " + aluno.getNomeDoAluno());
            System.out.println("  Matrícula: " + aluno.getMatricula());
            System.out.println("  Email: " + aluno.getEmail());
        } else {
            System.err.println("\n[!] Aluno com matrícula '" + matricula + "' não encontrado.");
        }
    }


	@Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nomeDoAluno + '\'' +
                ", matricula='" + matricula + '\'' +
                ", cre=" + cre +
                ", email='" + getEmail() + '\'' +
                '}';
	}

}
