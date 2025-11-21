package program;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Aluno;
import models.CentralDeInformacoes;
import models.Coordenador;
import models.Persistencia;
import models.Sexo;
import models.recurses.Disciplina;
import models.recurses.Edital;
import models.recurses.GeradorDeRelatorios;
import models.recurses.Mensageiro;

public class Main {

    public static void aguardarEnter(Scanner sc) {
        System.out.println("\n(Pressione ENTER para voltar ao menu...)");
        sc.nextLine();
    }

    public static void main(String[] args) {
        // Tenta recuperar os dados salvos (XML/Serialização)
        CentralDeInformacoes ci = Persistencia.recuperarCentral();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n========================================");
        System.out.println("   Sistema de Gerenciamento de Monitoria");
        System.out.println("========================================");
        
        while(true) {
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
            
            System.out.println("\n--- Administração ---");
            System.out.println(" 9. Cadastrar Coordenador"); // <--- NOVA OPÇÃO
            
            System.out.println("\n----------------------------------------");
            System.out.println(" S. Sair do Sistema");
            System.out.println("----------------------------------------");

            while(true) {
                // ... impressão do menu ...
                System.out.print("\nEscolha uma opção: ");
                String inputOp = sc.nextLine().toUpperCase();
                char op = inputOp.length() > 0 ? inputOp.charAt(0) : ' '; 

                if (op == '1') {
                    // AQUI: Chamamos o método estático da classe Aluno
                    Aluno.cadastrarViaConsole(sc, ci);
                    aguardarEnter(sc);

                } else if (op == '2') {
                    // AQUI: Chamamos a listagem
                    Aluno.listarViaConsole(ci);
                    aguardarEnter(sc);

                } else if (op == '3') {
                    // AQUI: Chamamos a busca
                    Aluno.buscarPorMatriculaViaConsole(sc, ci);
                    aguardarEnter(sc);
                    
	            } else if(op == '4') {
	                System.out.println("\n--- 4. Publicar Novo Edital ---");
	                
	                System.out.print("Número do Edital (Ex: 2025/01): ");
	                String numEdital = sc.nextLine();
	
	                ArrayList<Disciplina> d = new ArrayList<>();
	                int n = 0;
	                try {
	                    System.out.print("Quantas disciplinas este edital terá? ");
	                    n = Integer.parseInt(sc.nextLine());
	                } catch (NumberFormatException e) {
	                    System.err.println("[!] Valor inválido. Definindo como 0 disciplinas.");
	                }
	
	                for(int i = 0; i < n; i++) {
	                    System.out.println("\n--- Disciplina "+ (i+1) + "/" + n + " ---");
	                    System.out.print("Nome da disciplina: ");
	                    String nomeDisciplina = sc.nextLine();
	                    int qntdVagas = 0;
	                    try {
	                        System.out.print("Quantidade de vagas: ");
	                        qntdVagas = Integer.parseInt(sc.nextLine());
	                    } catch (NumberFormatException e) {
	                        System.err.println("[!] Valor inválido. Definindo como 0 vagas.");
	                    }
	                    d.add(new Disciplina(nomeDisciplina, qntdVagas));
	                }
	                
	                System.out.println("\n--- Período de Inscrição ---");
	                LocalDate dataInicio = null;
	                LocalDate dataLimite = null;
	                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                
	                boolean dataInicioValida = false;
	                while (!dataInicioValida) {
	                    System.out.print("Digite a data de INÍCIO (dd/MM/yyyy): ");
	                    String inputInicio = sc.nextLine();
	                    try {
	                        dataInicio = LocalDate.parse(inputInicio, formatador);
	                        dataInicioValida = true; 
	                    } catch (DateTimeParseException e) {
	                        System.err.println("[!] Formato inválido! Tente novamente.");
	                    }
	                }
	
	                boolean dataLimiteValida = false;
	                while (!dataLimiteValida) {
	                    System.out.print("Digite a data LIMITE (dd/MM/yyyy): ");
	                    String inputLimite = sc.nextLine();
	                    try {
	                        dataLimite = LocalDate.parse(inputLimite, formatador);
	                        if (dataLimite.isBefore(dataInicio)) {
	                            System.err.println("[!] Erro: A data limite não pode ser ANTERIOR à data de início.");
	                        } else {
	                            dataLimiteValida = true; 
	                        }
	                    } catch (DateTimeParseException e) {
	                        System.err.println("[!] Formato inválido! Tente novamente.");
	                    }
	                }
	                
	                boolean adicionarEdital = ci.adicionarEdital(new Edital(numEdital, dataInicio, dataLimite, d));
	                Persistencia.salvarCentral(ci);
	                System.out.println("\n[+] Edital " + numEdital + " publicado com sucesso!");
	                aguardarEnter(sc);
	                
	            } else if(op == '5') {
	                System.out.println("\n--- 5. Listar Editais Publicados ---");
	                ArrayList<Edital> editais = ci.getTodosOsEditais();
	                if (editais.isEmpty()) {
	                    System.out.println("\nNenhum edital cadastrado.");
	                } else {
	                    for (Edital edital : editais) {
	                    	System.out.println("- ID: " + edital.getId() + " | Edital Nº: " + edital.getNumEdital());
	                    }
	                    System.out.println("\nTotal de editais: " + editais.size());
	                }
	                aguardarEnter(sc);
	
	            } else if(op == '6') {
	                System.out.println("\n--- 6. Detalhar Edital por ID ---");
	                System.out.print("Digite o ID do edital: ");
	                try {
	                    long idEdital = Long.parseLong(sc.nextLine());
	                    Edital edital = ci.recuperarEditalPorId(idEdital);
	                    if (edital != null) {
	                        System.out.println("\n--- Detalhes do Edital ---");
	                        System.out.println(edital.toString()); 
	                    } else {
	                        System.err.println("\n[!] Edital com ID " + idEdital + " não encontrado.");
	                    }
	                } catch (NumberFormatException e) {
	                    System.err.println("\n[!] Erro: ID inválido. Digite apenas números.");
	                }
	                aguardarEnter(sc);
	
	            } else if(op == '7') {
	                System.out.println("\n--- 7. Inscrever Aluno em Edital ---");
	                try {
	                    System.out.print("Digite o ID do edital: ");
	                    long editalId = Long.parseLong(sc.nextLine());
	                    Edital editalEscolhido = ci.recuperarEditalPorId(editalId);
	
	                    if (editalEscolhido == null) {
	                        System.err.println("[!] Erro: Edital não encontrado.");
	                        aguardarEnter(sc);
	                        continue; 
	                    }
	
	                    System.out.print("Digite a matrícula do aluno: ");
	                    String matriculaAluno = sc.nextLine();
	                    Aluno alunoInscrito = ci.recuperarAlunoPorMatricula(matriculaAluno);
	
	                    if (alunoInscrito == null) {
	                        System.err.println("[!] Erro: Aluno não encontrado.");
	                        aguardarEnter(sc);
	                        continue; 
	                    }
	
	                    ArrayList<Disciplina> disciplinasDoEdital = editalEscolhido.getDisciplinas();
	                    if (disciplinasDoEdital.isEmpty()) {
	                        System.err.println("[!] Erro: Este edital não possui disciplinas cadastradas.");
	                        aguardarEnter(sc);
	                        continue;
	                    }
	
	                    System.out.println("\nDisciplinas disponíveis no edital " + editalEscolhido.getNumEdital() + ":");
	                    for (int i = 0; i < disciplinasDoEdital.size(); i++) {
	                        System.out.println("  " + (i + 1) + " - " + disciplinasDoEdital.get(i).getNomeDisciplina());
	                    }
	
	                    System.out.print("\nEscolha o número da disciplina: ");
	                    int escolhaDisciplina = Integer.parseInt(sc.nextLine());
	
	                    if (escolhaDisciplina < 1 || escolhaDisciplina > disciplinasDoEdital.size()) {
	                        System.err.println("[!] Erro: Opção de disciplina inválida.");
	                        aguardarEnter(sc);
	                        continue; 
	                    }
	                    
	                    Disciplina disciplinaEscolhida = disciplinasDoEdital.get(escolhaDisciplina - 1); 
	
	                    boolean sucesso = editalEscolhido.inscrever(alunoInscrito, disciplinaEscolhida);
	
	                    if (sucesso) {
	                        System.out.println("\n[+] Inscrição realizada com sucesso!");
	                        Persistencia.salvarCentral(ci); 
	                        
	                        try {
	                            String emailAluno = alunoInscrito.getEmail();
	                            String mensagemEmail = "Sua inscrição na disciplina " + disciplinaEscolhida.getNomeDisciplina() + " foi realizada.";
	                            Mensageiro.enviarEmail(emailAluno, mensagemEmail);
	                        } catch (Exception e) {
	                            System.err.println("[!] A inscrição foi salva, mas falhou ao enviar o e-mail.");
	                        }
	                        
	                    } else {
	                        System.err.println("[!] Não foi possível realizar a inscrição.");
	                    }
	
	                } catch (NumberFormatException e) {
	                    System.err.println("\n[!] Erro: ID ou número inválido.");
	                } catch (Exception e) {
	                    System.err.println("\n[!] Ocorreu um erro inesperado: " + e.getMessage());
	                }
	                aguardarEnter(sc);
	
	            } else if (op == '8') {
	                System.out.println("\n--- 8. Gerar Relatório de Inscrições (PDF) ---");
	                try {
	                    System.out.print("Digite o ID do edital: ");
	                    long editalId = Long.parseLong(sc.nextLine());
	
	                    System.out.print("Digite a matrícula do aluno: ");
	                    String matriculaAluno = sc.nextLine();
	                    
	                    GeradorDeRelatorios.gerarRelatorioInscricoes(matriculaAluno, editalId, ci);
	                
	                } catch (NumberFormatException e) {
	                    System.err.println("\n[!] Erro: ID inválido.");
	                } catch (Exception e) {
	                    System.err.println("\n[!] Ocorreu um erro inesperado: " + e.getMessage());
	                }
	                aguardarEnter(sc);
	            
	            // -------------------------------------------------------
	            // --- NOVA LÓGICA PARA CADASTRAR COORDENADOR (OPÇÃO 9) ---
	            // -------------------------------------------------------
	            }else if (op == '9') {
	                Coordenador.cadastrarViaConsole(sc, ci);
	                aguardarEnter(sc);
	            } else if (op == 'S') {
	                System.out.println("\nSaindo do sistema... Até logo!");
	                break;
	            } else {
	                System.err.println("\n[!] Opção inválida. Por favor, tente novamente.");
	                aguardarEnter(sc);
	            }
	        
            }
            sc.close();
            System.out.println("========================================");
        }
    }
}
