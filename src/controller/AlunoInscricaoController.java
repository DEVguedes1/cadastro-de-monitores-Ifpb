package controller;

/**
 * Controlador responsável por gerenciar o processo de inscrição de um aluno
 * em uma disciplina pertencente a um Edital.
 *
 * <p>Esta classe coordena:
 * <ul>
 *     <li>Interação da tela de inscrição com o modelo;</li>
 *     <li>Confirmação e validação da inscrição;</li>
 *     <li>Persistência das alterações na Central de Informações;</li>
 *     <li>Navegação entre telas do aluno.</li>
 * </ul>
 *
 * O fluxo geral é:
 * <ol>
 *     <li>O aluno seleciona uma disciplina;</li>
 *     <li>Confirma a inscrição;</li>
 *     <li>Informa sua média final;</li>
 *     <li>O sistema valida e registra a inscrição no Edital;</li>
 *     <li>A tela retorna automaticamente para a lista de editais.</li>
 * </ol>
 *
 * @author Seu Nome
 */

import view.AlunoInscricaoView;
import view.ListarEditaisAlunoView; // Importante para voltar
import models.CentralDeInformacoes;
import models.Persistencia;
import models.Aluno;
import models.recurses.Disciplina;
import models.recurses.Edital;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class AlunoInscricaoController implements ActionListener {

    private AlunoInscricaoView view;
    private Edital edital;
    private String nomeAlunoLogado;

    /**
     * Constrói o controlador e associa os listeners da interface.
     *
     * @param view tela de inscrição do aluno
     * @param edital edital no qual o aluno deseja se inscrever
     * @param nomeAluno nome do aluno logado
     */

    public AlunoInscricaoController(AlunoInscricaoView view, Edital edital, String nomeAluno) {
        this.view = view;
        this.edital = edital;
        this.nomeAlunoLogado = nomeAluno;

        this.view.addInscreverListener(this);
        
        // Configura o botão Voltar para reabrir a lista
        this.view.addVoltarListener(e -> voltarParaLista());
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        String nomeDisciplina = view.getDisciplinaSelecionada();

        if (nomeDisciplina == null) {
            view.mostrarMensagem("Selecione uma disciplina na tabela!");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(view, 
            "Deseja se inscrever para monitoria de " + nomeDisciplina + "?",
            "Confirmar Inscrição", JOptionPane.YES_NO_OPTION);
            
        if (resp == JOptionPane.YES_OPTION) {
            realizarInscricao(nomeDisciplina);
        }
    }
    
    /**
     * Executa todo o processo de inscrição após confirmação do aluno.
     *
     * @param nomeDisciplina nome da disciplina escolhida
     */

    private void realizarInscricao(String nomeDisciplina) {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        
        // 1. Busca o aluno no banco
        Aluno alunoLogado = null;
        for(Aluno a : central.getTodosOsAlunos()) {
            if(a.getNomeDoAluno().equalsIgnoreCase(nomeAlunoLogado)) {
                alunoLogado = a;
                break;
            }
        }

        if (alunoLogado == null) {
            view.mostrarMensagem("Erro: Aluno não encontrado no banco de dados.");
            return;
        }

        Edital editalAtual = central.recuperarEditalPorId(edital.getId());
        Disciplina disciplinaAlvo = editalAtual.buscarDisciplina(nomeDisciplina);

        // 2. Pergunta a nota
        String inputNota = JOptionPane.showInputDialog(view, "Qual foi sua Média Final nesta disciplina?");
        if (inputNota == null) return; // Cancelou
        
        try {
            double nota = Double.parseDouble(inputNota.replace(",", "."));
            
            // 3. Tenta inscrever
            boolean sucesso = editalAtual.inscrever(alunoLogado, disciplinaAlvo, nota);

            if (sucesso) {
                Persistencia.salvarCentral(central);
                
                // Mensagem de Sucesso
                view.mostrarMensagem("Inscrição realizada com sucesso!");
                
                // --- CORREÇÃO DO ERRO ---
                // Depois de dar OK na mensagem, volta automaticamente para a lista
                voltarParaLista(); 
                
            } else {
                view.mostrarMensagem("Não foi possível realizar a inscrição.\n(Verifique prazos ou duplicidade).");
            }

        } catch (NumberFormatException ex) {
            view.mostrarMensagem("Nota inválida! Digite apenas números (ex: 8.5).");
        }
    }

     /**
     * Fecha a tela atual e retorna para a lista de editais disponíveis ao aluno.
     */

    private void voltarParaLista() {
        view.dispose(); // Fecha a tela de inscrição
        
        // Abre a tela anterior (Lista de Editais)
        ListarEditaisAlunoView lista = new ListarEditaisAlunoView();
        lista.setVisible(true);
        new ListarEditaisAlunoController(lista, nomeAlunoLogado);
    }
}