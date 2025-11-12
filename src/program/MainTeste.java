package program;

import controller.EditalController;
import controller.LoginController;
import models.Aluno;
import models.Usuario;
import models.recurses.Disciplina;
import models.recurses.Edital;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainTeste {

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("     INICIANDO BATERIA DE TESTES         ");
        System.out.println("=========================================\n");

        LoginController loginController = new LoginController();
        
        // Limpa dados antigos para o teste começar "limpo"
        // (Cuidado: isso apaga seu json de verdade, ideal usar um arquivo separado para testes)
        loginController.limparBaseDados(); 

        // --- TESTE 1: Cadastro de Usuários ---
        System.out.println(">>> Teste 1: Cadastro de Usuários");
        try {
            Aluno aluno = new Aluno("Joao Teste", "202301", 8.5, "joao@gmail.com", "Senha@123");
            loginController.cadastrarUsuario(aluno);
            System.out.println("[SUCESSO] Aluno cadastrado.");
        } catch (Exception e) {
            System.out.println("[ERRO] Falha ao cadastrar aluno válido: " + e.getMessage());
        }

        // Teste de Validação (Senha Fraca)
        try {
            Aluno alunoFraco = new Aluno("Pedro Fraco", "202302", 7.0, "pedro@gmail.com", "123");
            loginController.cadastrarUsuario(alunoFraco);
            System.out.println("[ERRO] O sistema aceitou uma senha fraca!");
        } catch (IllegalArgumentException e) {
            System.out.println("[SUCESSO] Sistema rejeitou senha fraca corretamente: " + e.getMessage());
        }

        // --- TESTE 2: Login ---
        System.out.println("\n>>> Teste 2: Login");
        Usuario logado = loginController.fazerLogin("joao@gmail.com", "Senha@123");
        if (logado != null && logado instanceof Aluno) {
            System.out.println("[SUCESSO] Login realizado corretamente para: " + logado.getEmail());
        } else {
            System.out.println("[ERRO] Falha no login com credenciais válidas.");
        }

        Usuario falhaLogin = loginController.fazerLogin("joao@gmail.com", "SenhaErrada");
        if (falhaLogin == null) {
            System.out.println("[SUCESSO] Sistema bloqueou senha incorreta.");
        } else {
            System.out.println("[ERRO] Sistema permitiu login com senha errada!");
        }

        // --- TESTE 3: Edital e Disciplina ---
        System.out.println("\n>>> Teste 3: Regras de Edital");
        
        // Criar Disciplinas
        Disciplina d1 = new Disciplina("POO", 2);
        Disciplina d2 = new Disciplina("Banco de Dados", 1);
        ArrayList<Disciplina> listaDisc = new ArrayList<>();
        listaDisc.add(d1);
        listaDisc.add(d2);

        // Criar Edital Válido (Datas atuais)
        Edital editalAtual = new Edital("01/2025", LocalDate.now().minusDays(1), LocalDate.now().plusDays(5), listaDisc);
        
        // Tentar inscrever aluno
        if (logado instanceof Aluno) {
            boolean inscricaoSucesso = editalAtual.inscrever((Aluno) logado, d1);
            if (inscricaoSucesso) {
                System.out.println("[SUCESSO] Aluno inscrito na disciplina dentro do prazo.");
            } else {
                System.out.println("[ERRO] Falha ao inscrever aluno em edital válido.");
            }
        }

        // Testar Edital Vencido
        Edital editalVencido = new Edital("99/2020", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 1), listaDisc);
        if (logado instanceof Aluno) {
            boolean inscricaoVencida = editalVencido.inscrever((Aluno) logado, d1);
            if (!inscricaoVencida) {
                System.out.println("[SUCESSO] Sistema bloqueou inscrição em edital vencido.");
            } else {
                System.out.println("[ERRO] Sistema permitiu inscrição fora do prazo!");
            }
        }

        System.out.println("\n=========================================");
        System.out.println("          FIM DOS TESTES                 ");
        System.out.println("=========================================");
    }
}