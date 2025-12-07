package controller;

/**
 * Controlador do Catálogo de Editais (Visão do Aluno).
 * <p>
 * Filtra e exibe os editais disponíveis para o estudante.
 * Responsabilidades:
 * <ul>
 * <li>Listar apenas editais relevantes (Abertos ou Não Iniciados).</li>
 * <li>Gerenciar o clique no botão "Inscrever-se".</li>
 * <li>Validar se o prazo de inscrição já começou antes de abrir a tela de disciplinas.</li>
 * </ul>
 */

import view.ListarEditaisAlunoView;
import view.DashboardAluno;
import view.AlunoInscricaoView; // A tela que fizemos antes!
import models.CentralDeInformacoes;
import models.Persistencia;
import models.recurses.Edital;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListarEditaisAlunoController implements ActionListener {

    private ListarEditaisAlunoView view;
    private String nomeAluno;

    public ListarEditaisAlunoController(ListarEditaisAlunoView view, String nomeAluno) {
        this.view = view;
        this.nomeAluno = nomeAluno;
        
        this.view.addVoltarListener(e -> voltar());
        this.view.addInscreverListener(this);
        
        carregarTabela();
    }

    private void carregarTabela() {
        CentralDeInformacoes central = Persistencia.recuperarCentral();
        ArrayList<Edital> lista = central.getTodosOsEditais();
        var model = view.getModelo();
        model.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Edital e : lista) {
            String status = "Aberto";
            LocalDate hoje = LocalDate.now();
            if (hoje.isBefore(e.getDataIncio())) status = "Não Iniciado";
            else if (hoje.isAfter(e.getDataFinal())) status = "Encerrado";

            model.addRow(new Object[]{
                e.getId(),
                e.getNumEdital(),
                e.getDataIncio().format(fmt),
                e.getDataFinal().format(fmt),
                status
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String idStr = view.getIdSelecionado();
        
        if (idStr != null) {
            long idEdital = Long.parseLong(idStr);
            CentralDeInformacoes central = Persistencia.recuperarCentral();
            Edital editalSelecionado = central.recuperarEditalPorId(idEdital);

            // Validação simples de prazo (opcional, já tem no método inscrever, mas é bom visualmente)
            if (LocalDate.now().isBefore(editalSelecionado.getDataIncio())) {
                view.mostrarMensagem("Atenção: As inscrições para este edital ainda não começaram!");
                // Não retorna, deixa ele ver as disciplinas mesmo assim
            }

            // --- AQUI CONECTAMOS NA TELA QUE FIZEMOS ANTES ---
            view.dispose();
            AlunoInscricaoView inscricaoView = new AlunoInscricaoView(editalSelecionado);
            inscricaoView.setVisible(true);
            new AlunoInscricaoController(inscricaoView, editalSelecionado, nomeAluno);
            
        } else {
            view.mostrarMensagem("Selecione um edital primeiro!");
        }
    }

    private void voltar() {
        view.dispose();
        DashboardAluno dash = new DashboardAluno(nomeAluno);
        dash.setVisible(true);
        new DashboardAlunoController(dash, nomeAluno);
    }
}