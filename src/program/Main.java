package program;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import models.Aluno;
import models.CentralDeInformacoes;
import models.Coordenador;
import models.Persistencia;
import models.recurses.Disciplina;
import models.recurses.Edital;
import models.recurses.GeradorDeRelatorios;
import models.recurses.Mensageiro;

public class Main {
	public static final Scanner sc = new Scanner(System.in);

    public static void aguardarEnter(Scanner sc) {
        System.out.println("\n(Pressione ENTER para voltar ao menu...)");
        sc.nextLine();
    }

    public static void main(String[] args) {
        CentralDeInformacoes ci = Persistencia.carregarOuCriarCentral(sc);
        
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
			System.out.println(" 9. Editar Edital");
            
            System.out.println("\n--- Administração ---");
            System.out.println(" 10. Cadastrar Coordenador"); // <--- NOVA OPÇÃO
			System.out.println(" 11. Calcular o resultado do Edital de Monitoria"); // <--- NOVA OPÇÃO
            System.out.println("\n----------------------------------------");
            System.out.println(" S. Sair do Sistema");
            System.out.println("----------------------------------------");

            while(true) {
                // ... impressão do menu ...
                System.out.print("\nEscolha uma opção: ");
                String inputOp = sc.nextLine().toUpperCase();
                String op = inputOp;

                if (op.equals("1")) {
                    // AQUI: Chamamos o método estático da classe Aluno
                    Aluno.cadastrarViaConsole(sc, ci);
                    aguardarEnter(sc);

                } else if (op.equals("2")) {
                    // AQUI: Chamamos a listagem
                    Aluno.listarViaConsole(ci);
                    aguardarEnter(sc);

                } else if (op.equals("3")) {
                    // AQUI: Chamamos a busca
                    Aluno.buscarPorMatriculaViaConsole(sc, ci);
                    aguardarEnter(sc);
                    
	            } else if(op.equals("4")) {
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
						float pesoNota;
						float pesoCRE;
	                    try {
	                        System.out.print("Quantidade de vagas: ");
	                        qntdVagas = Integer.parseInt(sc.nextLine());
	                    } catch (NumberFormatException e) {
	                        System.err.println("[!] Valor inválido. Definindo como 0 vagas.");
	                    }
						try {
							System.out.println("Pesos da disciplina (Obs: A soma dos pesos deve ser 1)");
							System.out.println("Digite o peso da nota (0.1 a 0.9): ");
							pesoNota = Float.parseFloat(sc.nextLine());
							System.out.println("Digite o peso do CRE (0.1 a 0.9): ");
							pesoCRE = Float.parseFloat(sc.nextLine());
							if ((pesoNota + pesoCRE) != 1){
								System.out.println("[!] A soma dos pesos deve ser 1!!!");
								throw new Exception("Soma dos pesos inválida");
							}

						} catch (Exception e) {
							System.err.println("[!] Valor inválido. Definindo os pesos para 0.5 e 0.5 (1).");
							pesoNota = 0.5f;
							pesoCRE = 0.5f;
						}
	                    d.add(new Disciplina(nomeDisciplina, qntdVagas, pesoNota, pesoCRE));
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
					int maxInc = 1;
					try {
						System.out.print("Quantidade máxima de incrições por aluno: ");
						maxInc = Integer.parseInt(sc.nextLine());
					} catch (NumberFormatException e) {
						System.err.println("[!] Valor inválido. Definindo como 1.");
					}
	                boolean adicionarEdital = ci.adicionarEdital(new Edital(numEdital, dataInicio, dataLimite, d, maxInc));
	                Persistencia.salvarCentral(ci);
	                System.out.println("\n[+] Edital " + numEdital + " publicado com sucesso!");
	                aguardarEnter(sc);
	                
	            } else if(op.equals("5")) {
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
	
	            } else if(op.equals("6")) {
	                System.out.println("\n--- 6. Detalhar Edital por ID ---");
	                System.out.print("Digite o ID do edital: ");
	                try {
	                    long idEdital = Long.parseLong(sc.nextLine());
	                    Edital edital = ci.recuperarEditalPorId(idEdital);
	                    if (edital != null) {
	                        System.out.println("\n--- Detalhes do Edital ---");
	                        System.out.println(edital.toString()); 
							System.out.println("Menu de Opções: ");
							System.out.println("1. Encerrar Edital ");
							System.out.println("2. Editar Edital ");
							System.out.println("3. Clonar o Edital ");
							System.out.println("4. Sair ");
							System.out.print("Escolha uma das opções (1-3) ou S para sair: "); int SubMenu = sc.nextInt();
							if (SubMenu == 1){
								if (edital.encerrarEdital()){
									System.out.println("Encerrando edital: " + edital.getNumEdital());
								}
								else{
									System.out.println("[!] Este Edital já está encerrado.");
								}
							}
							else if (SubMenu == 2){
								System.out.println("Editar Edital: ");
								System.out.println("1. Editar data de início");
								System.out.println("2. Editar data final ");
								System.out.println("3. Reabrir edital ");
								System.out.println("4. Aumentar número de vagas");
								System.out.println("5. Editar pesos da fórmula de pontuação");
								System.out.println("6. Sair ");
								System.out.print("Escolha uma das opções (1-6): "); int SubMenu2 = sc.nextInt();
								sc.nextLine();
								if (SubMenu2 == 1){
									try {
										if (edital.getDataIncio().isBefore(LocalDate.now())){
											System.out.println("[!]Não é possível mudar a data de início deste edital. A data de ínicio desse edital já passou.");
										}
										else {
											System.out.println("Digite a nova data de início do edital (dd/MM/yyyy): ");
											String mudarData = sc.nextLine();
											DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
											LocalDate dataModificada = LocalDate.parse(mudarData, formatador);
											if (dataModificada.isBefore(LocalDate.now())){
												System.out.println("[!] Essa data já passou!");
											}
											else if (dataModificada.isBefore(edital.getDataIncio())){
												System.out.println("[!] A data de entrega não pode ser antes da data inicial!");
											}
											else{
												edital.setDataIncio(dataModificada);
												System.out.println("Nova data de ínicio do edital: " + edital.getDataIncio());
											}
										}
									} catch (DateTimeParseException e) {
										System.out.println("Error: Data inválida");
									}
								}
								else if (SubMenu2 == 2){
									System.out.println("Digite a nova data final do edital (dd/MM/yyyy): ");
									String mudarData = sc.nextLine();
									DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
									LocalDate dataModificada = LocalDate.parse(mudarData, formatador);
									if (dataModificada.isBefore(LocalDate.now())){
												System.out.println("[!] Essa data já passou!");
											}
									else{
										edital.setDataFinal(dataModificada);
										System.out.println("Nova data final do edital: " + edital.getDataFinal());
									}
								}
								else if (SubMenu2 == 3){
									if(edital.reabrirEdital()){
										System.out.println("O edital foi aberto!");
									}
									else{
										System.out.println("O Edital não pode ser aberto!");
									}
								}
								else if (SubMenu2 == 4){
									System.out.println("Digite qual disciplina: ");
									String buscarDisciplina = sc.nextLine();
									boolean achado = false;
									for (Disciplina d1 : edital.getDisciplinas()){
										if (d1.getNomeDisciplina().equalsIgnoreCase(buscarDisciplina)){
											System.out.println("Quantidade de vagas: " + d1.getQntdVagas());
											System.out.println("Digite a nova quantidade de vagas: ");
											int qtd = Integer.parseInt(sc.nextLine()); 
											if (qtd < d1.getQntdVagas()){
												System.out.println("[!] A quantidade de vagas não pode ser menor!");
											}
											else{
												achado = true;
												d1.setQntdVagas(qtd);
												System.out.println("Nova quantidade de vagas:" + d1.getQntdVagas());
											}
										}
									}
									if (!achado)
										System.out.println("[!] Essa disciplina não consta no edital!");
								}
								else if (SubMenu2 == 5){
									System.out.println("Digite qual disciplina: ");
									String buscarDisciplina = sc.nextLine();
									boolean achado = false;
									for (Disciplina d1 : edital.getDisciplinas()){
										if (d1.getNomeDisciplina().equalsIgnoreCase(buscarDisciplina)){
											achado = true;
											System.out.println("Pesos anteriores: CRE:" + d1.getPesoCRE() + "Nota Disciplina:" + d1.getPesoNota());
											System.out.println("Digite o novo peso do CRE: ");
											float cre = Float.parseFloat(sc.nextLine());
											System.out.println("Digite o novo peso da nota: ");
											float nota = Float.parseFloat(sc.nextLine());
											if ((cre + nota) != 1){
												System.out.println("[!] A soma dos pesos deve ser 1. Pesos não modificados!");
											}
											else{
												d1.setPesoCRE(cre);
												d1.setPesoNota(nota);
												System.out.println("Pesos definidos para: CRE: " + d1.getPesoCRE() + " Nota Disciplina: " + d1.getPesoNota());
											}
										}
									}
									if (!achado)
										System.out.println("[!] Essa disciplina não consta no edital!");
								}
								else if (SubMenu2 == 6){
									break;
								}
								else 
									System.out.println("Essa opção não consta no menu!");
							}
							else if (SubMenu == 3){
								Edital editalClonado = edital.clonarEdital();
								boolean adicionarEdital = ci.adicionarEdital(editalClonado);
	                			Persistencia.salvarCentral(ci);
	                			System.out.println("\n[+] Edital " + editalClonado.getNumEdital() + " clonado com sucesso!");
							}
							else if (SubMenu == 4){
								break;
							}
							else {
								System.out.println("[!]Essa opção não consta no menu!");
								break;
							}
							
	                    } else {
	                        System.err.println("\n[!] Edital com ID " + idEdital + " não encontrado.");
	                    }
	                } catch (NumberFormatException e) {
	                    System.err.println("\n[!] Erro: ID inválido. Digite apenas números.");
	                }
	                aguardarEnter(sc);
	
	            } else if(op.equals("7")) {
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
	
	            } else if (op.equals("8")) {
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
	            }else if (op.equals("9")) {
					System.out.println("\n--- 9. Editar Edital ---");
					try {
	                    System.out.print("Digite o ID do edital: ");
	                    long editalId = Long.parseLong(sc.nextLine());
						Edital edital = ci.recuperarEditalPorId(editalId);
						if (edital != null) {
							System.out.println("Editar Edital: ");
							System.out.println("1. Editar data de início");
							System.out.println("2. Editar data final ");
							System.out.println("3. Reabrir edital ");
							System.out.println("4. Aumentar número de vagas");
							System.out.println("5. Editar pesos da fórmula de pontuação");
							System.out.println("6. Sair ");
							System.out.print("Escolha uma das opções (1-6): "); int SubMenu2 = sc.nextInt();
							sc.nextLine();
							if (SubMenu2 == 1){
								try {
									if (edital.getDataIncio().isBefore(LocalDate.now())){
										System.out.println("[!]Não é possível mudar a data de início deste edital. A data de ínicio desse edital já passou.");
									}
									else {
										System.out.println("Digite a nova data de início do edital (dd/MM/yyyy): ");
										String mudarData = sc.nextLine();
										DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
										LocalDate dataModificada = LocalDate.parse(mudarData, formatador);
										if (dataModificada.isBefore(LocalDate.now())){
											System.out.println("[!] Essa data já passou!");
										}
										else if (dataModificada.isBefore(edital.getDataIncio())){
											System.out.println("[!] A data de entrega não pode ser antes da data inicial!");
										}
										else{
											edital.setDataIncio(dataModificada);
											System.out.println("Nova data de ínicio do edital: " + edital.getDataIncio());
										}
									}
								} catch (DateTimeParseException e) {
									System.out.println("Error: Data inválida");
								}
							}
							else if (SubMenu2 == 2){
								System.out.println("Digite a nova data final do edital (dd/MM/yyyy): ");
								String mudarData = sc.nextLine();
								DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								LocalDate dataModificada = LocalDate.parse(mudarData, formatador);
								if (dataModificada.isBefore(LocalDate.now())){
											System.out.println("[!] Essa data já passou!");
										}
								else{
									edital.setDataFinal(dataModificada);
									System.out.println("Nova data final do edital: " + edital.getDataFinal());
								}
							}
							else if (SubMenu2 == 3){
								if (edital.isAtivo()){
									System.out.println("[!] O Edital já está aberto!");
								}
								else{
									if(edital.reabrirEdital()){
									System.out.println("O edital foi aberto!");
									}
								}
							}
							else if (SubMenu2 == 4){
								System.out.println("Digite qual disciplina: ");
								String buscarDisciplina = sc.nextLine();
								boolean achado = false;
								for (Disciplina d1 : edital.getDisciplinas()){
									if (d1.getNomeDisciplina().equalsIgnoreCase(buscarDisciplina)){
										System.out.println("Quantidade de vagas: " + d1.getQntdVagas());
										System.out.println("Digite a nova quantidade de vagas: ");
										int qtd = Integer.parseInt(sc.nextLine()); 
										if (qtd < d1.getQntdVagas()){
											System.out.println("[!] A quantidade de vagas não pode ser menor!");
										}
										else{
											achado = true;
											d1.setQntdVagas(qtd);
											System.out.println("Nova quantidade de vagas:" + d1.getQntdVagas());
										}
									}
								}
								if (!achado)
									System.out.println("[!] Essa disciplina não consta no edital!");
							}
							else if (SubMenu2 == 5){
								System.out.println("Digite qual disciplina: ");
								String buscarDisciplina = sc.nextLine();
								boolean achado = false;
								for (Disciplina d1 : edital.getDisciplinas()){
									if (d1.getNomeDisciplina().equalsIgnoreCase(buscarDisciplina)){
										System.out.println("Pesos anteriores: CRE:" + d1.getPesoCRE() + "Nota Disciplina:" + d1.getPesoNota());
										System.out.println("Digite o novo peso do CRE: ");
										float cre = Float.parseFloat(sc.nextLine());
										d1.setPesoCRE(cre);
										System.out.println("Digite o novo peso da nota: ");
										float nota = Float.parseFloat(sc.nextLine());
										d1.setPesoNota(nota);
										System.out.println("Pesos definidos para: CRE: " + d1.getPesoCRE() + "Nota Disciplina" + d1.getPesoNota());
									}
								}
								if (!achado)
									System.out.println("[!] Essa disciplina não consta no edital!");
							}
					}
					} catch (NumberFormatException e) {
						System.err.println("\n[!] Erro: ID inválido.");
					} catch (Exception e) {
						System.err.println("\n[!] Ocorreu um erro inesperado: " + e.getMessage());
					}

				}else if (op.equals("10")) {
	                Coordenador.cadastrarViaConsole(sc, ci);
	                aguardarEnter(sc); 
				} else if (op.equals("11")) {
					System.out.println("\n--- 11. Calcular o resultado do Edital de Monitoria ---");
					System.out.print("Digite o ID do edital: ");
	                long editalId = Long.parseLong(sc.nextLine());
	                Edital editalEscolhido = ci.recuperarEditalPorId(editalId);
	
	                    if (editalEscolhido == null) {
	                        System.err.println("[!] Erro: Edital não encontrado.");
	                        aguardarEnter(sc);
	                        continue;
	                    }

				} else if (op.equals("S")) {
	                System.out.println("\nSaindo do sistema... Até logo!");
	                break;
				}else {
	                System.err.println("\n[!] Opção inválida. Por favor, tente novamente.");
	                aguardarEnter(sc);
	            }
	        
            }
            System.out.println("========================================");
        }
    }
}
