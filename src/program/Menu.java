package program;

import controller.LoginController;
import models.Aluno;
import models.Coordenador;
import models.Usuario;
import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final LoginController loginController = new LoginController();
    private Usuario usuarioLogado = null;

    public void exibirMenu() {
        while (true) {
            if (usuarioLogado == null) {
                exibirMenuPrincipal();
            } else {
                exibirMenuLogado();
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Login");
        System.out.println("2. Cadastro");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        int opcao = lerOpcao();

        switch (opcao) {
            case 1:
                fazerLogin();
                break;
            case 2:
                exibirMenuCadastro();
                break;
            case 3:
                System.out.println("Saindo do sistema...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void exibirMenuCadastro() {
        System.out.println("\n--- Menu de Cadastro ---");
        System.out.println("1. Cadastrar Aluno");
        System.out.println("2. Cadastrar Coordenador");
        System.out.println("3. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = lerOpcao();

        switch (opcao) {
            case 1:
                cadastrarAluno();
                break;
            case 2:
                cadastrarCoordenador();
                break;
            case 3:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void exibirMenuLogado() {
        while (usuarioLogado != null) {
            System.out.println("\n--- Menu de " + (usuarioLogado instanceof Aluno ? "Aluno" : "Coordenador") + " ---");
            System.out.println("Bem-vindo(a), " + usuarioLogado.getEmail() + "!");

            if (usuarioLogado instanceof Aluno) {
                System.out.println("1. Visualizar perfil");
                System.out.println("0. Logout");
                System.out.print("Escolha uma opção: ");
                int opcao = lerOpcao();
                switch (opcao) {
                    case 1 -> System.out.println(usuarioLogado);
                    case 0 -> {
                        usuarioLogado = null;
                        System.out.println("Logout realizado.");
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } else { // Coordenador
                System.out.println("1. Visualizar perfil");
                System.out.println("2. Listar todos os usuários");
                System.out.println("0. Logout");
                System.out.print("Escolha uma opção: ");
                int opcao = lerOpcao();
                switch (opcao) {
                    case 1 -> System.out.println(usuarioLogado);
                    case 2 -> loginController.listarUsuarios();
                    case 0 -> {
                        usuarioLogado = null;
                        System.out.println("Logout realizado.");
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        }
    }

   
    private void fazerLogin() {
        System.out.print("Digite o email: ");
        String email = sc.nextLine();
        System.out.print("Digite a senha: ");
        String senha = sc.nextLine();
        
        Usuario usuario = loginController.fazerLogin(email, senha);
        if (usuario != null) {
            this.usuarioLogado = usuario;
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private void cadastrarAluno() {
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
            double cre = Double.parseDouble(sc.nextLine());
            Aluno novoAluno = new Aluno(nome, matricula, cre, email, senha);
            loginController.cadastrarUsuario(novoAluno);
        } catch (NumberFormatException e) {
            System.out.println("Valor de CRE inválido. O cadastro falhou.");
        }
    }

    private void cadastrarCoordenador() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        Coordenador novoCoordenador = new Coordenador(nome, email, senha);
        loginController.cadastrarUsuario(novoCoordenador);
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}