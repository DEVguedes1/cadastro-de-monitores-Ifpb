package controller;

/**
 * Controlador da Tela de Configuração de Editais.
 * <p>
 * Responsável pela manipulação das disciplinas de um edital específico.
 * Aplica regras de negócio críticas, como:
 * <ul>
 * <li>Validação da soma dos pesos (Nota + CRE deve ser 1.0).</li>
 * <li>Persistência imediata das alterações no XML.</li>
 * <li>Atualização dinâmica da tabela de disciplinas na View.</li>
 * </ul>
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.CentralDeInformacoes;
import models.Persistencia;
import models.recurses.Disciplina;
import models.recurses.Edital;
import view.DetalharEditalView;
import view.EditarDisciplinaDialog;
import view.ListarEditaisView;

public class DetalharEditalController implements ActionListener {

    private DetalharEditalView view;
    private Edital editalAtual;
    private String nomeCoordenador;
    private long idEdital;

    public DetalharEditalController(DetalharEditalView view, long idEdital, String nomeCoord) {
        this.view = view;
        this.idEdital = idEdital;
        this.nomeCoordenador = nomeCoord;

        this.view.addAdicionarListener(this);
        this.view.addVoltarListener(e -> voltar());
        
        this.view.addEditarDiscListener(e -> editarDisciplina());
        this.view.addExcluirDiscListener(e -> excluirDisciplina());

        carregarEdital();
    }

    private void carregarEdital() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        this.editalAtual = central.recuperarEditalPorId(idEdital);

        if (editalAtual == null) {
            view.mostrarMensagem("Erro: Edital não encontrado!");
            voltar();
            return;
        }

        // Preenche cabeçalho
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        view.setTextoTitulo("Edital: " + editalAtual.getNumEdital());
        view.setTextoPeriodo("Inscrições: " + editalAtual.getDataIncio().format(fmt) + 
                             " até " + editalAtual.getDataFinal().format(fmt));

        atualizarTabela();
    }

    private void atualizarTabela() {
        var model = view.getModeloTabela();
        model.setRowCount(0);
        
        ArrayList<Disciplina> disciplinas = editalAtual.getDisciplinas();
        if (disciplinas != null) {
            for (Disciplina d : disciplinas) {
                model.addRow(new Object[]{
                    d.getNomeDisciplina(),
                    d.getQntdVagas(),
                    d.getPesoNota(),
                    d.getPesoCRE()
                });
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica de Adicionar Disciplina
        try {
            String nome = view.getNomeDisc();
            int vagas = Integer.parseInt(view.getVagas());
            float pesoNota = Float.parseFloat(view.getPesoNota());
            float pesoCRE = Float.parseFloat(view.getPesoCRE());

            if (nome.isEmpty()) {
                view.mostrarMensagem("Digite o nome da disciplina.");
                return;
            }

            // --- REGRA DE NEGÓCIO: Soma deve ser 1.0 ---
            // Usamos uma pequena margem de erro (0.001) porque float as vezes dá 0.999999
            if (Math.abs((pesoNota + pesoCRE) - 1.0) > 0.001) {
                view.mostrarMensagem("Erro: A soma dos pesos (Nota + CRE) deve ser igual a 1.0!\nExemplo: Nota 0.6 + CRE 0.4 = 1.0");
                return;
            }

            // Cria a nova disciplina 
            // (Usando construtor que você forneceu. Docente e Período deixei padrão por enquanto)
            Disciplina nova = new Disciplina(nome, vagas, pesoNota, pesoCRE, "A definir", 0);

            // Adiciona ao edital e Salva
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            
            // Precisamos buscar o edital "vivo" dentro da central de novo pra salvar
            Edital editalSalvar = central.recuperarEditalPorId(idEdital);
            
            // Garante que a lista não é nula
            if (editalSalvar.getDisciplinas() == null) {
                editalSalvar.setDisciplinas(new ArrayList<>());
            }
            
            editalSalvar.getDisciplinas().add(nova);
            
            Persistencia.salvarCentral(central);
            
            view.mostrarMensagem("Disciplina adicionada com sucesso!");
            view.limparCampos();
            
            // Atualiza a tela com os dados novos
            this.editalAtual = editalSalvar; 
            atualizarTabela();

        } catch (NumberFormatException ex) {
            view.mostrarMensagem("Erro: Verifique os números. Use ponto (.) para decimais.");
        }
    }

    private void voltar() {
        view.dispose();
        ListarEditaisView lista = new ListarEditaisView();
        lista.setVisible(true);
        new ListarEditaisController(lista, nomeCoordenador);
    }
    
    private void editarDisciplina() {
        String nomeDisc = view.getDisciplinaSelecionada();
        if (nomeDisc == null) {
            view.mostrarMensagem("Selecione uma disciplina na tabela.");
            return;
        }

        Disciplina alvo = editalAtual.buscarDisciplina(nomeDisc);
        if (alvo != null) {
            EditarDisciplinaDialog dialog = new EditarDisciplinaDialog(view, alvo);
            
            new EditarDisciplinaController(dialog, alvo, () -> {
                // Ao terminar, atualiza a tabela
                atualizarTabela();
            });
            
            dialog.setVisible(true);
        }
    }

    private void excluirDisciplina() {
        String nomeDisc = view.getDisciplinaSelecionada();
        if (nomeDisc == null) {
            view.mostrarMensagem("Selecione uma disciplina para excluir.");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(view, 
            "Tem certeza que deseja excluir a disciplina '" + nomeDisc + "'?",
            "Excluir", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            Disciplina alvo = editalAtual.buscarDisciplina(nomeDisc);
            if (alvo != null) {
                // Remove da lista
                editalAtual.getDisciplinas().remove(alvo);
                
                // Salva
                CentralDeInformacoes central = Persistencia.recuperarCentral();
                // Atualiza o edital no banco
                Edital eBanco = central.recuperarEditalPorId(editalAtual.getId());
                eBanco.setDisciplinas(editalAtual.getDisciplinas());
                
                Persistencia.salvarCentral(central);
                
                view.mostrarMensagem("Disciplina removida.");
                atualizarTabela();
            }
        }
    }
}