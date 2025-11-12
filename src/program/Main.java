package program;

import controller.EditalController;
import controller.LoginController;
import models.Aluno;
import models.Sexo;
import models.recurses.Disciplina;
import models.recurses.Edital;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Dependências estáticas para serem usadas dentro da main
    private static final Scanner sc = new Scanner(System.in);
    private static final LoginController loginController = new LoginController();
    private static final EditalController editalController = new EditalController();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        
        while (true) {
            System.out.println("\n--- Alunos ---");
            System.out.println(" 1. Cadastrar Aluno");
            System.out.println(" 2. Listar Todos os Alunos");
            System.out.println(" 3. Buscar Aluno por Matrícula");

            System.out.println("\n--- Editais ---");
            System.out.println(" 4. Publicar Novo Edital");
            System.out.println(" 5. Listar Editais Publicados");
            System.out.println(" 6. Detalhar Edital por ID");
            System.out.println(" 7. Inscrever Aluno em Edital");
            System.out.println(" 8. Gerar Relatório de Inscrições (PDF)");

            System.out.println("\n----------------------------------------");
            System.out.println(" S. Sair do Sistema");
            System.out.println("----------------------------------------");
            System.out.print("Escolha uma opção: ");

            String opcao = sc.nextLine().toUpperCase();

            switch (opcao) {
                case "1" -> cadastrarAluno();
                case "2" -> listarAlunos();
                case "3" -> buscarAlunoMatricula();
                case "4" -> publicarEdital();
                case "5" -> listarEditais();
                case "6" -> detalharEdital();
                case "7" -> inscreverAluno();
                case "8" -> gerarRelatorio();
                case "S" -> {
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // --- MÉTODOS AUXILIARES DE ALUNO ---

    private static void cadastrarAluno() {
    	double cre = 0;
        System.out.println("\n>>> Novo Aluno");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("CRE: ");
        try {
            cre = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: O CRE deve ser um número.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
        try {
        	String sx = sc.nextLine();
        	Sexo sexo = Sexo.valueOf(sx);
        	Aluno a = new Aluno(nome, matricula, cre, email, senha, sexo);
        	loginController.cadastrarUsuario(a);
        }catch(Exception e) {
        	 System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listarAlunos() {
        System.out.println("\n>>> Lista de Alunos");
        List<Aluno> alunos = loginController.listarApenasAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            alunos.forEach(System.out::println);
        }
    }

    private static void buscarAlunoMatricula() {
        System.out.print("Digite a matrícula para busca: ");
        String mat = sc.nextLine();
        Aluno a = loginController.buscarAlunoPorMatricula(mat);
        if (a != null) {
            System.out.println("Aluno encontrado: " + a);
        } else {
            System.out.println("Aluno com matrícula '" + mat + "' não encontrado.");
        }
    }

    // --- MÉTODOS AUXILIARES DE EDITAL ---

    private static void publicarEdital() {
        System.out.println("\n>>> Publicar Edital");
        try {
            System.out.print("Número do Edital (ex: 01/2024): ");
            String num = sc.nextLine();

            System.out.print("Data Início (dd/MM/yyyy): ");
            LocalDate inicio = LocalDate.parse(sc.nextLine(), dtf);

            System.out.print("Data Final (dd/MM/yyyy): ");
            LocalDate fim = LocalDate.parse(sc.nextLine(), dtf);

            ArrayList<Disciplina> disciplinas = new ArrayList<>();
            while (true) {
                System.out.print("Adicionar disciplina? (s/n): ");
                String resp = sc.nextLine();
                if (!resp.equalsIgnoreCase("s")) break;

                System.out.print("  Nome da disciplina: ");
                String nomeDisc = sc.nextLine();
                System.out.print("  Quantidade de vagas: ");
                int vagas = Integer.parseInt(sc.nextLine());
                disciplinas.add(new Disciplina(nomeDisc, vagas));
            }

            Edital novo = new Edital(num, inicio, fim, disciplinas);
            editalController.publicarEdital(novo);

        } catch (Exception e) {
            System.out.println("Erro ao criar edital (verifique as datas): " + e.getMessage());
        }
    }

    private static void listarEditais() {
        System.out.println("\n>>> Editais Publicados");
        List<Edital> editais = editalController.listarEditais();
        if (editais.isEmpty()) {
            System.out.println("Nenhum edital publicado.");
        } else {
            for (Edital e : editais) {
                System.out.println("ID: " + e.getId() + " | Edital Nº: " + e.getNumEdital());
            }
        }
    }

    private static void detalharEdital() {
        System.out.print("Digite o ID do edital: ");
        try {
            long id = Long.parseLong(sc.nextLine());
            Edital e = editalController.buscarEditalPorId(id);
            if (e != null) {
                System.out.println(e.toString());
            } else {
                System.out.println("Edital não encontrado.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("ID inválido.");
        }
    }

    private static void inscreverAluno() {
        System.out.println("\n>>> Inscrição em Monitoria");
        System.out.print("Digite a Matrícula do Aluno: ");
        String mat = sc.nextLine();
        Aluno aluno = loginController.buscarAlunoPorMatricula(mat);

        if (aluno == null) {
            System.out.println("Erro: Aluno não encontrado.");
            return;
        }

        System.out.print("Digite o ID do Edital: ");
        try {
            long idEdital = Long.parseLong(sc.nextLine());
            
            System.out.print("Nome da Disciplina (conforme edital): ");
            String nomeDisc = sc.nextLine();

            boolean sucesso = editalController.inscreverAluno(idEdital, aluno, nomeDisc);
            if (sucesso) {
                System.out.println("Inscrição realizada com sucesso para " + aluno.getNomeDoAluno());
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void gerarRelatorio() {
        System.out.println("\n>>> Gerando Relatório (Simulação PDF)...");
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("========================================\n");
        relatorio.append("      RELATÓRIO GERAL DE MONITORIA      \n");
        relatorio.append("========================================\n\n");

        List<Edital> editais = editalController.listarEditais();
        if (editais.isEmpty()) {
            relatorio.append("Nenhum edital registrado.\n");
        } else {
            for (Edital e : editais) {
                relatorio.append("EDITAL ").append(e.getNumEdital()).append("\n");
                relatorio.append("Período: ").append(e.getDataIncio()).append(" a ").append(e.getDataFinal()).append("\n");
                
                for (Disciplina d : e.getDisciplinas()) {
                    relatorio.append("  > Disciplina: ").append(d.getNomeDisciplina())
                             .append(" (").append(d.getQntdVagas()).append(" vagas)\n");
                    
                    if (d.getAlunos().isEmpty()) {
                        relatorio.append("      * Nenhum inscrito.\n");
                    } else {
                        for (Aluno a : d.getAlunos()) {
                            relatorio.append("      * Inscrito: ").append(a.getNomeDoAluno())
                                     .append(" (Mat: ").append(a.getMatricula()).append(")\n");
                        }
                    }
                }
                relatorio.append("----------------------------------------\n");
            }
        }

        System.out.println(relatorio.toString());
        System.out.println("[SISTEMA] Arquivo exportado com sucesso (Simulação).");
    }
}